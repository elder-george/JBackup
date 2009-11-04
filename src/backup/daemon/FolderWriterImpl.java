/*
 */

package backup.daemon;

import backup.protocol.FileRecord;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Date;

/**
 *
 * @author Yuri Korchyomkin
 */
public class FolderWriterImpl implements FolderWriter{

    File folder;

    public FolderWriterImpl(File folder) {
        this.folder = folder;
    }
    
    public FileRecord[] getStoredFiles() {
        File[] files = folder.listFiles();
        FileRecord[] records = new FileRecord[files.length];
        for(int i = 0; i < files.length; i++){
            records[i] = new FileRecord(files[i].getName(), new Date(files[i].lastModified()));
        }
        return records;
    }

    @Override
    public void deleteFile(String filename) throws IOException {
        File file = findFile(filename);
        if(file != null)
            file.delete();
    }
    
    File findFile(String filename){
        for(File file: folder.listFiles())
            if(file.getName().equals(filename))
                return file;
        return null;
    }

    @Override
    public void updateFile(String filename, int offset, byte[] bytes) throws IOException{
        assert(offset > 0);
        File file = findFile(filename);
        if(file == null)
            file = new File(folder, filename);
        RandomAccessFile stream = new RandomAccessFile(file, "rw");
        try{
            stream.seek(offset);
            stream.write(bytes);
        }finally{
            stream.close();
        }
    }

}
