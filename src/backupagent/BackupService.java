package backupagent;

/**
 *
 * @author Yuri Korchyomkin
 */
public interface BackupService {
    String[] getMonitoredFilesList();
    void deleteFiles(String[] deleted);
    void updateFiles(String[] names, byte[][] contents);
}
