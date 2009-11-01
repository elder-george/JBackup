package backup.daemon.commands;

import backup.protocol.Commands;
import backup.protocol.Request;
import backup.protocol.Response;
import java.io.InputStream;

/**
 *
 * @author Yuri Korchyomkin
 */
public class DeleteFileRequest extends Request{
    private final String filename;

    public DeleteFileRequest(String filename) {
        super(Commands.DELETE_FILE);
        this.filename = filename;
    }

    @Override
    public void readAdditionalData(InputStream in) {
    }

    @Override
    public Response process() {
        return new OKResponse();
    }
}
