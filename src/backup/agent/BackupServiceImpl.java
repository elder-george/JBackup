package backup.agent;

import backup.protocol.FileRecord;
import backup.agent.commands.DeleteFileRequest;
import backup.agent.commands.GetListRequest;
import backup.agent.commands.GetListResponse;
import backup.agent.commands.OKResponse;
import backup.agent.commands.SyncDirectoryRequest;
import backup.agent.commands.UpdateFileRequest;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.util.Date;

/**
 *
 * @author Yuri Korchyomkin
 *
 * Default implementation of BackupService interface.
 *
 * For interaction with server it uses Request and Response implementations sent
 * and received via streams.
 *
 * Streams may be received from socket instance.
 */
public class BackupServiceImpl implements BackupService{
    private final OutputStream out;
    private final InputStream in;

    /**
     * Initializes instance of BackupServiceImpl
     * @param out stream that will be used for sending requests.
     * @param in stream that will be used for receiving responses.
     */
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
        StringBuffer buffer = new StringBuffer();
        int symbol;
        while((symbol = in.read()) != -1)
        {
            if(symbol == '\n' || symbol == '\r' || symbol == Character.LINE_SEPARATOR)
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
    public void updateFile(String fileName, Date modificationDate, int offset, byte[] contents, int size)
        throws IOException, ParseException
    {
        UpdateFileRequest request = new UpdateFileRequest(fileName, offset, contents, size);
        request.send(out);
        OKResponse response = new OKResponse(readLine(in));
        response.readAdditionalData(in);
    }

    @Override
    public void setMonitoredDirectory(String directory) throws IOException, ParseException{
        SyncDirectoryRequest request = new SyncDirectoryRequest(directory);
        request.send(out);
        OKResponse response = new OKResponse(readLine(in));
        response.readAdditionalData(in);
    }
}
