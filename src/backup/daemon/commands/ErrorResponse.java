/*
 */

package backup.daemon.commands;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

/**
 *
 * @author Yuri Korchyomkin
 */
public class ErrorResponse extends Response{
    private final String message;

    public ErrorResponse(String message){
        this.message = message.replaceAll("\r|\n", " ");
    }

    @Override
    public void writeResponse(OutputStream out) throws IOException {
        OutputStreamWriter wr = new OutputStreamWriter(out);
        wr.write("ERROR " +message+ (char)Character.LINE_SEPARATOR);
        wr.flush();
    }

}
