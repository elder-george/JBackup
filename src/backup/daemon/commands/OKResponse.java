package backup.daemon.commands;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

/**
 * Server-sider representation of response containing no additional information.
 * @author Yuri Korchyomkin
 */
public class OKResponse extends Response {

    @Override
    public void writeResponse(OutputStream out) throws IOException {
        OutputStreamWriter wr = new OutputStreamWriter(out);
        wr.write("OK|" + (char)Character.LINE_SEPARATOR);
        wr.flush();
    }

}
