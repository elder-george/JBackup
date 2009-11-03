/*
 */

package backup.agent.commands;

import backup.protocol.Commands;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.text.MessageFormat;

/**
 *
 * @author Yuri Korchyomkin
 */
public class UpdateFileRequest implements Request{
    private final String filename;
    private final int offset;
    private final byte[] bytes;

    public UpdateFileRequest(String filename, int offset, byte[] bytes){
        this.filename = filename;
        this.offset = offset;
        this.bytes = bytes;
    }

    public void send(OutputStream out) throws IOException {
        OutputStreamWriter wr = new OutputStreamWriter(out);
        //TODO: reimplement with MessageFormat (didn't work for the first time)
        wr.write(Commands.UPDATE_FILE+" "+ filename+" "+ offset+" "+ bytes.length+"\n");
        wr.flush();
        out.write(bytes);
        out.flush();
    }
}
