package backup.daemon.commands;

import backup.agent.commands.GetListResponse;
import backup.daemon.RequestFactory;
import backup.daemon.RequestFactoryImpl;
import backup.daemon.Session;
import backup.protocol.Commands;
import backup.protocol.SocketAutoConnector;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Yuri Korchyomkin
 */
public class GetListTest {

    SocketAutoConnector connector;
    RequestFactory factory = new RequestFactoryImpl();
    Session session;

    @Before
    public void setUp() throws Exception {
        connector = new SocketAutoConnector();
        session = new Session("localhost");
    }

    @After
    public void tearDown() throws Exception {
        connector.close();
    }

    /**
     * Test of process method, of class GetListRequest.
     */
    @Test
    public void testProcess() throws Exception{
        System.out.println("process");
        // sending request to server
        backup.agent.commands.GetListRequest clientSideRequest = new backup.agent.commands.GetListRequest();
        clientSideRequest.send(connector.getClientOut());
        // receiving request on server
        String requestString = readLine(connector.getServerIn());
        assertTrue(requestString.startsWith(Commands.GET_FILE_LIST));
        Request serverSideRequest = factory.createRequest(session, requestString);
        serverSideRequest.readAdditionalData(connector.getServerIn());
        // processing request and writing response to client
        serverSideRequest.process().writeResponse(connector.getServerOut());
        // receiving response on client
        String responseString = readLine(connector.getClientIn());
        backup.agent.commands.Response clientSideResponse = new GetListResponse(responseString);
        clientSideResponse.readAdditionalData(connector.getClientIn());
    }

    private String readLine(InputStream in) throws IOException{
        BufferedReader rdr = new BufferedReader(new InputStreamReader(in));
        return rdr.readLine();
    }

}