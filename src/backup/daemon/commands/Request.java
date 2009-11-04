package backup.daemon.commands;

import java.io.IOException;
import java.io.InputStream;

/**
 * Base class for all request classes.
 * @author Yuri Korchyomkin
  */
public abstract class Request {
    protected String commandText;

    protected Request(String commandText){
        this.commandText = commandText;
    }

    /**
     * Reads additional data from stream if necessary.
     * @param in
     * @throws IOException
     */
    public abstract void readAdditionalData(InputStream in) throws IOException;

    /**
     * Performs requested operation and returns a response with description of result.
     * @return response describing result (error, success, success + data).
     */
    public abstract Response process();
}
