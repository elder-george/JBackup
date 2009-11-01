package backup.agent;

import java.io.IOException;
import java.io.InputStream;

/**
 *
 * @author Yuri Korchyomkin
 */
public interface Folder {
    String getName();
    FileRecord[] getFiles();
    InputStream openFile(FileRecord file) throws IOException;
}
