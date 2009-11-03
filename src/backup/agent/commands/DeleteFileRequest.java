/*
 */

package backup.agent.commands;

import backup.protocol.Commands;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

/**
 *
 * @author Yuri Korchyomkin
 */
public class DeleteFileRequest implements Request{
    private final String filename;

    public DeleteFileRequest(String filename){
        this.filename = filename;
    }

    public void send(OutputStream out) throws IOException {
        OutputStreamWriter wr = new OutputStreamWriter(out);
        wr.write(Commands.DELETE_FILE + " "+filename +(char)Character.LINE_SEPARATOR);
        wr.flush();
    }
}
