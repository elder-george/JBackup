package backup.agent.commands;

import backup.protocol.Commands;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

/**
 * Client-side implementation of SyncDirectory request.
 * In case of success OK responses should be received.
 * Typical request looks like: "SYNC_DIR|my_directory\n"
 * @author Yuri Korchyomkin
 */
public class SyncDirectoryRequest implements Request{
    private final String directory;

    public SyncDirectoryRequest(String directory){
        this.directory = directory;
    }

    @Override
    public void send(OutputStream out) throws IOException {
        OutputStreamWriter wr = new OutputStreamWriter(out);
        wr.write(Commands.SYNC_DIRECTORY+"|"+directory + (char)Character.LINE_SEPARATOR);
        wr.flush();
        out.flush();        
    }

}
