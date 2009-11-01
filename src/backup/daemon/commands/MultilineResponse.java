package backup.daemon.commands;

import backup.protocol.Response;
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
    public void writeResponse(OutputStream out) {
        /*CharBuffer buffer = out.asCharBuffer();
        buffer.append("OK "+ lines.length + "\n");
        for(String line : lines)
            buffer.append(line + "\n");
         */
    }
}
