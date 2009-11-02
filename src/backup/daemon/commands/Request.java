package backup.daemon.commands;

import java.io.InputStream;

/**
 *
 * @author Yuri Korchyomkin
 */
public abstract class Request {
    protected String commandText;

    protected Request(String commandText){
        this.commandText = commandText;
    }

    public abstract void readAdditionalData(InputStream in);

    public abstract Response process();
}
