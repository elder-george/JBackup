package backup.agent.commands;

import backup.protocol.Commands;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

/**
 * Client-side object corresponding to request for list of files stored by server.
 * In case of success GetListResponse should be received.
 * Typical request looks like "GET_LIST\n"
 * @author Yuri Korchyomkin
 */
public class GetListRequest implements Request{

    @Override
    public void send(OutputStream out) throws IOException {
        OutputStreamWriter wr = new OutputStreamWriter(out);
        wr.write(Commands.GET_FILE_LIST+(char)Character.LINE_SEPARATOR);
        wr.flush();
        out.flush();
    }
}
