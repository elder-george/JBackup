package backup.daemon.commands;

import backup.protocol.Commands;
import backup.protocol.Request;
import backup.protocol.Response;
import java.io.InputStream;

/**
 *
 * @author Yuri Korchyomkin
 */
public class GetListRequest extends Request {
    private final String directory;

    public GetListRequest(String directory) {
        super(Commands.GET_FILE_LIST);
        this.directory = directory;
    }

    @Override
    public void readAdditionalData(InputStream in) {
    }

    @Override
    public Response process() {
        return new MultilineResponse(new String[0]);
    }
}
