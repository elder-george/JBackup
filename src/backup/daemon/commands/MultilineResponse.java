package backup.daemon.commands;

import backup.protocol.Response;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 *
 * @author Yuri Korchyomkin
 */
public class MultilineResponse extends Response {
    private final String[] lines;

    public MultilineResponse(String[] lines){
        this.lines = lines;
    }
    @Override
    public void writeResponse(OutputStream out) throws IOException{
        DataOutputStream buffer = new DataOutputStream(out);
        buffer.writeBytes("OK "+ lines.length + "\n");
        for(String line : lines)
            buffer.writeBytes(line + "\n");
        out.flush();
    }
}
