package backup.daemon.commands;

import backup.daemon.Session;
import backup.protocol.Commands;
import java.io.InputStream;

/**
 *
 * @author Yuri Korchyomkin
 * This request should be the first command from agent.
 */
public class SyncDirectoryRequest extends Request {
    private final Session session;
    private final String directory;

    public SyncDirectoryRequest(Session session, String directory) {
        super(Commands.SYNC_DIRECTORY);
        this.session = session;
        this.directory = directory;
    }

    @Override
    public void readAdditionalData(InputStream in) {
    }

    @Override
    public Response process() {
        session.setDirectory(directory);
        return new OKResponse();
    }

}
