/*
 */

package backup.agent;

import java.util.Date;

/**
 *
 * @author Yuri Korchyomkin
 */
public class BackupServiceImpl implements BackupService{

    public FileRecord[] getMonitoredFilesList() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void deleteFile(String fileName) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void updateFile(String fileName, Date modificationDate, int offset, byte[] contents) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
