package backup.daemon.commands;

import backup.daemon.Session;
import backup.protocol.Commands;
import java.io.IOException;
import java.io.InputStream;

/**
 * Server-side representation of delete file request.
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
        try{
            session.getFolderWriter().deleteFile(filename);
            return new OKResponse();
        }catch(IOException ex){
            return new ErrorResponse(ex.getMessage());
        }
    }
}
