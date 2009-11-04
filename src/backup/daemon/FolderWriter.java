/*
 */

package backup.daemon;

import backup.protocol.FileRecord;
import java.io.IOException;

/**
 * Interface of objects providing operations over backup folder.
 * @author Yuri Korchyomkin
 */
public interface FolderWriter {
    /**
     * Provides information about backupped files.
     * @return list of filenames and modification dates.
     */
    FileRecord[] getStoredFiles();
    /**
     * Deletes file from backup folder.
     * @param filename name of file to delete.
     * @throws IOException
     */
    void deleteFile(String filename) throws IOException;
    /**
     * Modifies file; writes chunk of bytes starting from given offset.
     * If file doesn't exist, it will be created.
     * @param filename name of file to modify.
     * @param offset offset at which data writing will be started.
     * @param bytes portion of data to write.
     * @throws IOException
     */
    void updateFile(String filename, int offset, byte[] bytes) throws IOException;
}
