package backupagent;

import java.util.Date;

/**
 *
 * @author Yuri Korchyomkin
 */
public interface BackupService {
    FileRecord[] getMonitoredFilesList();
    void deleteFile(String fileName);
    public void updateFile(String fileName, Date modificationDate, int offset, byte[] contents);
}
