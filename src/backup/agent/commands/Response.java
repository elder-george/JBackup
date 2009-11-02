package backup.agent.commands;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;

/**
 *
 * @author Yuri Korchyomkin
 */
public interface Response {
    void readAdditionalData(InputStream in) throws IOException, ParseException;
}
