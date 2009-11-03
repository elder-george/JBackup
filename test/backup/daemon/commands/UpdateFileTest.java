/*
 */

package backup.daemon.commands;

import backup.agent.commands.UpdateFileRequest;
import backup.protocol.Commands;
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
public class UpdateFileTest {

    SocketAutoConnector connector;

    @Before
    public void setUp()throws Exception {
        connector = new SocketAutoConnector();
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
        String filename = "1.txt";
        int offset = 42;
        byte[] bytes = new byte[16*1024];   // more than usually
        for(int i = 0; i < bytes.length; i++)
            bytes[i] = (byte)(i % 256);
        // send request to client
        backup.agent.commands.UpdateFileRequest clientRequest = new UpdateFileRequest(filename, offset, bytes);
        clientRequest.send(connector.getClientOut());
        // reading request at server
        String[] requestString = readLine(connector.getServerIn()).split(" ");
        assertEquals(Commands.UPDATE_FILE, requestString[0]);
        assertEquals(filename, requestString[1]);
        int actualOffset = Integer.valueOf(requestString[2]);
        int size = Integer.valueOf(requestString[3]);
        assertEquals(offset, actualOffset);
        assertEquals(bytes.length, size);
        backup.daemon.commands.UpdateFileRequest serverRequest = new backup.daemon.commands.UpdateFileRequest(filename, offset, size);
        serverRequest.readAdditionalData(connector.getServerIn());
        // sensing response
        backup.daemon.commands.Response serverResponse =  serverRequest.process();
        serverResponse.writeResponse(connector.getServerOut());

        String responseString = readLine(connector.getClientIn());
        assertTrue(responseString.startsWith(Responses.OK));
        
    }

}
