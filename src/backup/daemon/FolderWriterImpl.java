package backup.daemon;

import backup.protocol.FileRecord;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Date;

/**
 * Default implementation of FolderWriter interface.
 * @author Yuri Korchyomkin
 */
public class FolderWriterImpl implements FolderWriter{

    File folder;

    public FolderWriterImpl(File folder) {
        this.folder = folder;
        if(!folder.exists()){
            folder.mkdir();
            System.out.println("Folder "+ folder.getAbsolutePath() + " wasn't found; folder created");
        }
    }
    
    public synchronized FileRecord[] getStoredFiles() {
        File[] files = folder.listFiles();
        if(files == null) files = new File[0];
        FileRecord[] records = new FileRecord[files.length];
        for(int i = 0; i < files.length; i++){
            records[i] = new FileRecord(files[i].getName(), new Date(files[i].lastModified()));
        }
        return records;
    }

    @Override
    public synchronized void deleteFile(String filename) throws IOException {
        File file = findFile(filename);
        if(file != null)
            file.delete();
    }
    
    File findFile(String filename){
        File[] files = folder.listFiles();
        if(files == null) return null;
        for(File file: files)
            if(file.getName().equals(filename))
                return file;
        return null;
    }

    @Override
    public synchronized void updateFile(String filename, int offset, byte[] bytes) throws IOException{
        assert(offset >= 0);
        File file = findFile(filename);
        if(file == null){
            file = new File(folder, filename);
            file.createNewFile();
            System.out.println("File "+ file.getAbsolutePath() + " not found; file created.");
        }
        RandomAccessFile stream = new RandomAccessFile(file, "rw");
        try{
            stream.seek(offset);
            stream.write(bytes);
        }finally{
            stream.close();
        }
    }

}
