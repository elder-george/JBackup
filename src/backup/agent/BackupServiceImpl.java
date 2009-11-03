/*
 */

package backup.agent;

import backup.agent.commands.DeleteFileRequest;
import backup.agent.commands.DeleteFileResponse;
import backup.agent.commands.GetListRequest;
import backup.agent.commands.GetListResponse;
import backup.agent.commands.OKResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.text.ParseException;
import java.util.Date;

/**
 *
 * @author Yuri Korchyomkin
 */
public class BackupServiceImpl implements BackupService{
    private final OutputStream out;
    private final InputStream in;

    public BackupServiceImpl(OutputStream out, InputStream in){
        this.out = out;
        this.in = in;
    }

    @Override
    public FileRecord[] getMonitoredFilesList() throws IOException, ParseException {
        GetListRequest rq = new GetListRequest();
        rq.send(out);
        GetListResponse response = new GetListResponse(readLine(in));
        response.readAdditionalData(in);
        return response.getFiles();
    }

    String readLine(InputStream in) throws IOException{
        InputStreamReader rdr = new InputStreamReader(in);
        StringBuffer buffer = new StringBuffer();
        int symbol;
        while((symbol = rdr.read()) != -1)
        {
            if(symbol == Character.LINE_SEPARATOR)
                break;
            buffer.append((char)symbol);
        }
        return buffer.toString();
    }
    
    @Override
    public void deleteFile(String fileName) throws IOException, ParseException {
        DeleteFileRequest request = new DeleteFileRequest(fileName);
        request.send(out);
        OKResponse response = new OKResponse(readLine(in));
        response.readAdditionalData(in);
    }
    
    @Override
    public void updateFile(String fileName, Date modificationDate, int offset, byte[] contents) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
