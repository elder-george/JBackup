package backup.agent;

import backup.protocol.FileRecord;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

/**
 * Default implementation of FolderReader interface.
 * @author Yuri Korchyomkin
 */
public class FolderReaderImpl implements FolderReader{

    final String name;
    final File folder;
    /**
     * Initializes instance of FolderReaderImpl class.
     * @param directoryName name of directory. If this directory doesn't exists, it will be created.
     */
    public FolderReaderImpl(String directoryName){
        name = directoryName;
        this.folder = new File(directoryName);
        if(!this.folder.exists())
            this.folder.mkdir();
    }

    public String getName() {
        return name;
    }

    public FileRecord[] getFiles() {
        File[] filesInFolder = folder.listFiles();
        if(filesInFolder == null)
            filesInFolder = new File[0];
        FileRecord[] files = new FileRecord[filesInFolder.length];
        for(int i= 0;i<files.length; i++){
            File currFile = filesInFolder[i];
            files[i] = new FileRecord(currFile.getName(), new Date(currFile.lastModified()));
        }
        return files;

    }

    public InputStream openFile(FileRecord file) throws IOException {
        File f = new File(folder, file.getName());
        System.out.println("Opening file "+ f.getAbsolutePath());
        return new FileInputStream(f);
    }

}
