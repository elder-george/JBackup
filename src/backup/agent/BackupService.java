package backup.agent;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

/**
 *
 * @author Yuri Korchyomkin
 */
public interface BackupService {
    FileRecord[] getMonitoredFilesList()
            throws IOException, ParseException;
    void deleteFile(String fileName)
            throws IOException, ParseException;
    public void updateFile(String fileName, Date modificationDate, int offset, byte[] contents) 
            throws IOException, ParseException;
}
