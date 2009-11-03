/*
 */

package backup.daemon;

import backup.protocol.FileRecord;

/**
 *
 * @author Yuri Korchyomkin
 */
public interface FolderWriter {
    FileRecord[] getStoredFiles();
    void deleteFile(String filename);
    void updateFile(String filename, int offset, byte[] bytes);
}
