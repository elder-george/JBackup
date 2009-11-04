/*
 */

package backup.agent.commands;

import backup.protocol.Commands;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 *
 * @author Yuri Korchyomkin
 */
public class SyncDirectoryRequest implements Request{
    private final String directory;

    public SyncDirectoryRequest(String directory){
        this.directory = directory;
    }

    @Override
    public void send(OutputStream out) throws IOException {
        DataOutputStream wr = new DataOutputStream(out);
        wr.writeChars(Commands.SYNC_DIRECTORY+" "+directory + (char)Character.LINE_SEPARATOR);
    }

}
