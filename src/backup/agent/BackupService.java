package backup.agent;

import backup.protocol.FileRecord;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

/**
 *
 * @author Yuri Korchyomkin
 *
 * Interface of daemon accessible to client.
 */
public interface BackupService {

    /**
     * Sets monitored folder for this instance of BackupService. This method must be called first.
     * @param directory directory that will be monitored
     * @throws IOException
     * @throws ParseException
     */
    void setMonitoredDirectory(String directory) throws IOException, ParseException;
    /**
     *
     * @return list of records describing files.
     * @throws IOException
     * @throws ParseException
     */
    FileRecord[] getMonitoredFilesList()
            throws IOException, ParseException;
    /**
     * Deletes file in backup folder.
     * @param fileName name of file to delete.
     * @throws IOException
     * @throws ParseException
     */
    void deleteFile(String fileName)
            throws IOException, ParseException;
    /**
     * Writes portion of data at the file in backup directory.
     * This method is used for both creating and updating files.
     * @param fileName name of file to modify.
     * @param modificationDate date of file modification.
     * @param offset offset at which data must be written
     * @param contents piece of data
     * @param size size of data in contents array. Always less or equal of contents' length
     * @throws IOException
     * @throws ParseException
     */
    void updateFile(String fileName, Date modificationDate, int offset, byte[] contents, int size)
            throws IOException, ParseException;
}
