/*
 */

package backup.daemon.commands;

import backup.protocol.Commands;
import java.io.IOException;
import java.io.InputStream;

/**
 *
 * @author Yuri Korchyomkin
 */
public class UpdateFileRequest extends Request{
    private final String filename;
    private final int offset;
    private final int size;
    byte[] data;

    public UpdateFileRequest(String filename, int offset, int size){
        super(Commands.UPDATE_FILE);
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
        return new OKResponse();
    }

}
