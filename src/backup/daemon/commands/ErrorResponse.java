/*
 */

package backup.daemon.commands;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

/**
 * Represents description of error sent to client.
 * @author Yuri Korchyomkin
 */
public class ErrorResponse extends Response{
    private final String message;

    /**
     * Initializes instance of ErrorResponse
     * @param message error description.
     */
    public ErrorResponse(String message){
        //Due to client-side response parsing we can't put new-line charaters into response.
        this.message = message.replaceAll("\r|\n", " ");
    }

    @Override
    public void writeResponse(OutputStream out) throws IOException {
        OutputStreamWriter wr = new OutputStreamWriter(out);
        wr.write("ERROR|" +message+ (char)Character.LINE_SEPARATOR);
        wr.flush();
    }

}
