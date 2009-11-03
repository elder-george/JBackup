/*
 */

package backup.daemon.commands;

import backup.daemon.RequestFactory;
import backup.daemon.RequestFactoryImpl;
import backup.daemon.Session;
import backup.protocol.Commands;
import backup.protocol.SocketAutoConnector;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
/**
 *
 * @author Yuri Korchyomkin
 */
public class DeleteFileTest {
    SocketAutoConnector connector;
    RequestFactory factory = new RequestFactoryImpl();
    Session session;

    @Before
    public void setUp() throws Exception{
         connector = new SocketAutoConnector();
         session = new Session("localhost");
    }

    @After
    public void tearDown() throws Exception{
        connector.close();
    }

    @Test
    public void testDeleteFileRequestIsSentAndResponseIsReceived() throws Exception{
       String filename = "1.txt";
       backup.agent.commands.DeleteFileRequest clientRequest = new backup.agent.commands.DeleteFileRequest(filename);
       clientRequest.send(connector.getClientOut());
       
       String requestString = readLine(connector.getServerIn());
       assertTrue(requestString.startsWith(Commands.DELETE_FILE));
       backup.daemon.commands.Request serverRequest = factory.createRequest(null, requestString);
       serverRequest.readAdditionalData(connector.getServerIn());

       backup.daemon.commands.Response serverResponse = serverRequest.process();
       serverResponse.writeResponse(connector.getServerOut());

       String responseString = readLine(connector.getClientIn());
       assertTrue(responseString.startsWith("OK"));
       backup.agent.commands.OKResponse clientResponse = new backup.agent.commands.OKResponse(responseString);
       clientResponse.readAdditionalData(connector.getClientIn());
    }

    private String readLine(InputStream in) throws IOException{
        BufferedReader rdr = new BufferedReader(new InputStreamReader(in));
        return rdr.readLine();
    }

}
