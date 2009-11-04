/*
 */

package backup.daemon;

import backup.protocol.FileRecord;
import java.io.IOException;

/**
 *
 * @author Yuri Korchyomkin
 */
public interface FolderWriter {
    FileRecord[] getStoredFiles();
    void deleteFile(String filename) throws IOException;
    void updateFile(String filename, int offset, byte[] bytes) throws IOException;
}
