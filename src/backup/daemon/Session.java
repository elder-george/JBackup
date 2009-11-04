package backup.daemon;

import java.io.File;

/**
 * Session object associated with current connection.
 * @author Yuri Korchyomkin
 */
public class Session {
    private final String machineName;
    private String directory;
    File rootDirectory;

    FolderWriter writer;

    /**
     * Initializes session object.
     * Note, that it won't be fully functional until setDirectory() will be called.
     * @param rootDirectory root directory common for all sessions.
     * @param machineName name of machine. Files are stored in a sub-directory of
     * directory "rootDirectory/machineName"
     */
    public Session(File rootDirectory, String machineName) {
        this.rootDirectory = new File(rootDirectory, machineName);
        if(!rootDirectory.exists())
            rootDirectory.mkdir();
        this.machineName = machineName;

    }

    /**
     * @return the machineName
     */
    public String getMachineName() {
        return machineName;
    }

    /**
     * @return the directory
     */
    public String getDirectory() {
        return directory;
    }

    /**
     * @param directory the directory to set
     */
    public void setDirectory(String directory) {
        String[] directoryParts = directory.split("[\\/\\\\]"); // @"[\/\\]" in C#
        // I don't want to solve problems with nested folders, so I will only take last part
        this.directory = directoryParts[directoryParts.length -1];
    }

    /**
     * return FolderWriter object that can be used for file operations on server.
     * Note that this method should be called only after call to setDirectory();
     * @return
     */
    public synchronized FolderWriter getFolderWriter(){
        if(writer == null){
            assert(this.directory != null);
            File sessionFolder = new File(rootDirectory, directory);
            if(sessionFolder.exists()){
                sessionFolder.mkdir();
            }
            writer = new FolderWriterImpl(sessionFolder);
        }
        return writer;
    }
}
