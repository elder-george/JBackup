package backup.daemon;

/**
 *
 * @author Yuri Korchyomkin
 */
public class Session {
    private final String machineName;
    private String directory;

    Session(String machineName) {
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
    public String getDirectoryName() {
        return directory;
    }

    /**
     * @param directory the directory to set
     */
    public void setDirectory(String directory) {
        this.directory = directory;
    }


}
