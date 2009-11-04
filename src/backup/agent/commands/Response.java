package backup.agent.commands;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;

/**
 * Interface of client-side response objects.
 * @author Yuri Korchyomkin
 */
public interface Response {
    /**
     * Reads data from stream if necessary (usually it is not).
     * @param in
     * @throws IOException
     * @throws ParseException
     */
    void readAdditionalData(InputStream in) throws IOException, ParseException;
}
