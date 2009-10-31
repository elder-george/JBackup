/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package backup.agent;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

/**
 *
 * @author Yuri Korchyomkin
 */
public class FolderImpl implements Folder{

    final String name;
    public FolderImpl(String folder){
        name = folder;
    }

    public String getName() {
        return name;
    }

    public FileRecord[] getFiles() {
        File folder = new File(name);
        File[] filesInFolder = folder.listFiles();
        FileRecord[] files = new FileRecord[filesInFolder.length];
        for(int i= 0;i<files.length; i++){
            File currFile = filesInFolder[i];
            files[i] = new FileRecord(currFile.getName(), new Date(currFile.lastModified()));
        }
        return files;

    }

    public InputStream openFile(FileRecord file) throws IOException {
        return new FileInputStream(file.getName());
    }

}
