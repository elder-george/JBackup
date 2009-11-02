package backup.agent.commands;

import java.io.IOException;
import java.io.OutputStream;

/**
 *
 * @author Yuri Korchyomkin
 */
public interface Request {
    void send(OutputStream out) throws IOException;
}
