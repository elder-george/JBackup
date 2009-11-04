package backup.daemon.commands;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Base class for response objects.
 * @author Yuri Korchyomkin
  */
public abstract class Response {


    public abstract void writeResponse(OutputStream out) throws IOException;
}
