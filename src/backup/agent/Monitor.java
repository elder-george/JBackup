package backup.agent;

import backup.protocol.FileRecord;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.Collection;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * Thread implementing directory monitoring.
 * It should be started with method start(). At the end method stop() should be called.
 * @author Yuri Korchyomkin
 */
class Monitor implements Runnable{
    /**
     * Size of bytes chunk used for data transfer.
     */
    public static final int SEND_BUFFER_SIZE = 1024 * 1024;
    final BackupService service;
    final Thread monitorThread;
    final Semaphore shouldStop;

    final FolderReader folder;
    final int timeoutMilliseconds;

    /**
     * Initializes monitor.
     * @param folder monitored folder
     * @param service server interface object.
     * @param timeoutMilliseconds timeout before folder synchronization attempts.
     */
    public Monitor(FolderReader folder, BackupService service, int timeoutMilliseconds){
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
            // at the start we must notify server about folder we want to monitor.
            service.setMonitoredDirectory(this.folder.getName());
            System.out.println("Monitored directory set");
            do{
                // We receive list of files stored at server.
                FileRecord[] monitoredFiles = service.getMonitoredFilesList();
                // and list of files in monitored folder.
                FileRecord[] actualFiles = folder.getFiles();
                // building diff between this lists.
                DirectoryDiff diff = new DirectoryDiff(monitoredFiles, actualFiles);
                if(diff.getCreated().size()!= 0 && diff.getDeleted().size() != 0 && diff.getChanged().size() != 0)
                    System.out.printf("Created: %d; Deleted: %d; Changed: %d\n",
                        diff.getCreated().size(), diff.getDeleted().size(), diff.getChanged().size());
               // applying changes to server.
                deleteFiles(diff.getDeleted());
                createFiles(diff.getCreated());
                updateFiles(diff.getChanged());
            } // we are waiting for stop signal or timeout
            while(!shouldStop.tryAcquire(timeoutMilliseconds, TimeUnit.MILLISECONDS));
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

    /**
     * Signals thread to stop.
     */
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
                    bytesRead = stream.read(buffer,0, buffer.length);
                    if(bytesRead == -1)break;
                    service.updateFile(file.getName(), file.getModificationDate(), offset, buffer, bytesRead);
                    offset += bytesRead;
                }while(bytesRead == SEND_BUFFER_SIZE);
            }
            finally{
                if(stream != null)
                    stream.close();
            }
        }
        catch(IOException ex){
            System.err.printf("Transmitting file %s failed due to following reason: %s\n",
                                file.getName(), ex.getMessage());
        }

    }
}
