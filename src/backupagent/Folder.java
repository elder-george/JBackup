/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package backupagent;

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
