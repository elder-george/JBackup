/*
 */

package backup.daemon.commands;

import backup.daemon.Session;
import backup.protocol.Commands;
import java.io.IOException;
import java.io.InputStream;

/**
 *
 * @author Yuri Korchyomkin
 */
public class UpdateFileRequest extends Request{
    private final Session session;
    private final String filename;
    private final int offset;
    private final int size;
    byte[] data;

    public UpdateFileRequest(Session session, String filename, int offset, int size){
        super(Commands.UPDATE_FILE);
        this.session = session;
        this.filename = filename;
        this.offset = offset;
        this.size = size;

    }

    @Override
    public void readAdditionalData(InputStream in) throws IOException{
        data = new byte[size];
        if(in.read(data) == -1)
            throw new IOException("Can't read data");
    }

    @Override
    public Response process() {
        try{
            session.getFolderWriter().updateFile(filename, offset, data);
            return new OKResponse();
        }catch(IOException ex){
            return new ErrorResponse(ex.getMessage());
        }
    }

}
