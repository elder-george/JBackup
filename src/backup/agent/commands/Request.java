package backup.agent.commands;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Interface of client-side request objects.
 * @author Yuri Korchyomkin
 */
public interface Request {
    /**
     * Sends request through given stream.
     * @param out stream used for data transfer.
     * @throws IOException
     */
    void send(OutputStream out) throws IOException;
}
