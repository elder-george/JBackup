/*
 */

package backup.agent.commands;

import backup.protocol.Commands;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

/**
 * Client-side implementation of UpdateFile request.
 * In case of success OK responses should be received.
 * Typical request looks like: "UPD_FILE|1.txt|0|65536\n"
 * After this header 65536 bytes of data come.
 * @author Yuri Korchyomkin
 */
public class UpdateFileRequest implements Request{
    private final String filename;
    private final int offset;
    private final byte[] bytes;
    private final int size;

    public UpdateFileRequest(String filename, int offset, byte[] bytes, int size){
        assert(size <= bytes.length);
        this.filename = filename;
        this.offset = offset;
        this.bytes = bytes;
        this.size = size;
    }

    public void send(OutputStream out) throws IOException {
        OutputStreamWriter wr = new OutputStreamWriter(out);
        //TODO: reimplement with MessageFormat (didn't work for the first time)
        wr.write(Commands.UPDATE_FILE+"|"+ filename+"|"+ offset+"|"+ size+"\n");
        wr.flush();
        out.flush();
        out.write(bytes, 0, size);
        out.flush();
    }
}
