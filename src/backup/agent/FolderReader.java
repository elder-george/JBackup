package backup.agent;

import backup.protocol.FileRecord;
import java.io.IOException;
import java.io.InputStream;

/**
 * Interface of operation over folder in client.
 * @author Yuri Korchyomkin
 */
public interface FolderReader {
    /**
     * Name of folder.
     * @return
     */
    String getName();
    /**
     * List of records for files in this folder.
     * @return
     */
    FileRecord[] getFiles();
    /**
     * Opens file in this folder for reading.
     * @param file
     * @return
     * @throws IOException
     */
    InputStream openFile(FileRecord file) throws IOException;
}
