package backup.protocol;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;

/**
 *
 * @author Yuri Korchyomkin
 */
public abstract class Response {


    public abstract void writeResponse(OutputStream out) throws IOException;
}
