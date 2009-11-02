package backup.agent.commands;

import backup.protocol.Commands;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

/**
 *
 * @author Yuri Korchyomkin
 */
public class GetListRequest implements Request{

    @Override
    public void send(OutputStream out) throws IOException {
        OutputStreamWriter wr = new OutputStreamWriter(out);
        wr.write(Commands.GET_FILE_LIST+(char)Character.LINE_SEPARATOR);
        wr.flush();
    }
}
