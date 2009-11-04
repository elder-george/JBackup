/*
 */

package backup.daemon;

import java.io.File;
import java.text.ParseException;

/**
 * Option parser for Daemon process.
 * @author Yuri Korchyomkin
 */
public class DaemonOptions {
    int port;
    File rootDirectory;

    public DaemonOptions(String[] args) throws ParseException{
        try{
            port = Integer.valueOf(args[0]);
            rootDirectory = new File(args[1]);
            assert(rootDirectory.exists());
        }catch(Throwable e){
            throw new ParseException("Error while parsing arguments.\n"+
                    "Usage: BackupDaemon <port> <backup dir>\n" +
                    "Backup directory must exist", -1);
        }
    }

    public int getPort(){
        return port;
    }

    public File getRootDirectory(){
        return rootDirectory;
    }
}
