package backup.daemon.commands;

import backup.daemon.Session;
import backup.protocol.Commands;
import java.io.InputStream;

/**
 *
 * @author Yuri Korchyomkin
 */
public class DeleteFileRequest extends Request{
    private final Session session;
    private final String filename;

    public DeleteFileRequest(Session session, String filename) {
        super(Commands.DELETE_FILE);
        this.session = session;
        this.filename = filename;
    }

    @Override
    public void readAdditionalData(InputStream in) {
    }

    @Override
    public Response process() {
        session.getFolderWriter().deleteFile(filename);
        return new OKResponse();
    }
}
