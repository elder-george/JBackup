package backup.agent;

import backup.protocol.FileRecord;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.Collection;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Yuri Korchyomkin
 */
class Monitor implements Runnable{
    static final int SEND_BUFFER_SIZE = 65536;
    final BackupService service;
    final Thread monitorThread;
    final Semaphore shouldStop;

    final Folder folder;
    final int timeoutMilliseconds;

    public Monitor(Folder folder, BackupService service, int timeoutMilliseconds){
        this.folder = folder;
        this.service = service;
        this.monitorThread = new Thread(this, "monitor");
        this.shouldStop = new Semaphore(0);
        this.timeoutMilliseconds = timeoutMilliseconds;
    }

    public void start(){
        monitorThread.start();
    }

    public void run(){
        System.out.println("Monitoring started");
        try{
            while(!shouldStop.tryAcquire(timeoutMilliseconds, TimeUnit.MILLISECONDS)){
                FileRecord[] monitoredFiles = service.getMonitoredFilesList();
                FileRecord[] actualFiles = folder.getFiles();
                DirectoryDiff diff = new DirectoryDiff(monitoredFiles, actualFiles);
                System.out.printf("Created: %d; Deleted: %d; Changed: %d\n",
                        diff.getCreated().size(), diff.getDeleted().size(), diff.getChanged().size());
                deleteFiles(diff.getDeleted());
                createFiles(diff.getCreated());
                updateFiles(diff.getChanged());
            }
        }
        catch (IOException ex) {
            System.err.println("I/O error: "+ex.getMessage());
        }        catch (ParseException ex) {
            System.err.println("Data parsing error: "+ex.getMessage());
        }        catch(InterruptedException ex){
            System.err.println("Monitor thread was interrupted");
        }
        System.out.println("Monitoring stopped");
    }

    public void stop(){
        try
        {
            shouldStop.release();
            monitorThread.join();
        }
        catch(InterruptedException ex){
            System.err.println("Monitor thread was interrupted");
        }
    }

    private void createFiles(Collection<FileRecord> created) throws ParseException {
        for(FileRecord file : created){
            sendFileContents(file);
        }

    }

    private void deleteFiles(Collection<FileRecord> deleted) throws IOException, ParseException {
        for(FileRecord file : deleted){
            service.deleteFile(file.getName());
        }
    }

    private void updateFiles(Collection<FileRecord> changed) throws ParseException {
        for(FileRecord file : changed){
            sendFileContents(file);
        }
    }

    private void sendFileContents(FileRecord file) throws ParseException{
        try{
            InputStream stream = null;
            try{
                stream = folder.openFile(file);
                byte[] buffer = new byte[SEND_BUFFER_SIZE];
                int offset = 0;
                int bytesRead;
                do{
                    bytesRead = stream.read(buffer,offset, buffer.length);
                    service.updateFile(file.getName(), file.getModificationDate(), offset, buffer);
                    offset += bytesRead;
                }while(bytesRead == SEND_BUFFER_SIZE);
            }
            finally{
                if(stream != null)
                    stream.close();
            }
        }
        catch(IOException ex){
            System.err.printf("Transmitting file %s failed due to following reason: %s",
                                file.getName(), ex.getMessage());
        }

    }
}
