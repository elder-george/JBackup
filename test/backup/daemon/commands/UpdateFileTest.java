/*
 */

package backup.daemon.commands;

import backup.agent.commands.UpdateFileRequest;
import backup.daemon.FolderWriter;
import backup.daemon.Session;
import backup.protocol.Commands;
import backup.protocol.FileRecord;
import backup.protocol.Responses;
import backup.protocol.SocketAutoConnector;
import java.io.IOException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
/**
 *
 * @author Yuri Korchyomkin
 */
public class UpdateFileTest extends Session implements FolderWriter {

    SocketAutoConnector connector;
    String actualFilename;
    int actualOffset;
    byte[] actualBytes;

    public UpdateFileTest() {
        super("localhost");
    }

    @Before
    public void setUp()throws Exception {
        connector = new SocketAutoConnector();
        actualFilename = null;
        actualOffset = -1;
        actualBytes = null;
    }

    @After
    public void tearDown()throws Exception {
        connector.close();
    }

    String readLine(java.io.InputStream in) throws IOException{
        int symbol;
        StringBuffer buffer = new StringBuffer();
        while((symbol = in.read()) != -1){
            if(symbol == '\n' || symbol == '\r' || symbol == Character.LINE_SEPARATOR)
                break;
            buffer.append((char) symbol);
        }
        return buffer.toString();
    }

    @Test
    public void testUpdateFileRequestIsSentAndResponseReceived() throws Exception{
        System.out.println("UpdateFileRequest is sent and response received");
        String filename = "1.txt";
        int offset = 42;
        byte[] bytes = new byte[16*1024];   // more than usually
        for(int i = 0; i < bytes.length; i++)
            bytes[i] = (byte)(i % 256);
        // send request to client
        backup.agent.commands.UpdateFileRequest clientRequest = new UpdateFileRequest(filename, offset, bytes);
        clientRequest.send(connector.getClientOut());
        // reading request at server
        String requestString = readLine(connector.getServerIn());
        String[] requestParts = requestString.split(" ");
        assertEquals(Commands.UPDATE_FILE, requestParts[0]);
        assertEquals(filename, requestParts[1]);
        int requestOffset = Integer.valueOf(requestParts[2]);
        int size = Integer.valueOf(requestParts[3]);
        assertEquals(offset, requestOffset);
        assertEquals(bytes.length, size);
        backup.daemon.commands.UpdateFileRequest serverRequest = new backup.daemon.commands.UpdateFileRequest(this,filename, offset, size);
        serverRequest.readAdditionalData(connector.getServerIn());
        // sensing response
        backup.daemon.commands.Response serverResponse =  serverRequest.process();

        assertEquals(filename, actualFilename);
        assertEquals(offset, actualOffset);
        assertArrayEquals(actualBytes, bytes);

        serverResponse.writeResponse(connector.getServerOut());

        String responseString = readLine(connector.getClientIn());
        assertTrue(responseString.startsWith(Responses.OK));
        
    }

    public FileRecord[] getStoredFiles() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void deleteFile(String filename) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void updateFile(String filename, int offset, byte[] bytes) {
        this.actualFilename = filename;
        this.actualBytes = bytes;
        this.actualOffset = offset;
    }



    @Override
    public FolderWriter getFolderWriter(){
        return this;
    }
}
