/*
 */

package backup.protocol;

import java.io.IOException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Yuri Korchyomkin
 */
public class SocketAutoConnectorTest {
    private SocketAutoConnector connector;

    @Before
    public void setUp() throws Exception{
        connector = new SocketAutoConnector();
    }

    @After
    public void tearDown() throws IOException{
        if(connector != null)
            connector.close();
    }

    @Test
    public void testSocketAutoConnectorCanBeCreated(){ 
        System.out.println("SocketAutoConnector can be created");
    }

    @Test
    public void testClientCanWriteToServer() throws Exception{
        System.out.println("Client can write to Server using SocketAutoConnector");
        int sent = 42;

        connector.getClientOut().write(sent);
        connector.getClientOut().flush();
        assertEquals(sent, connector.getServerIn().read());
    }

    @Test
    public void testServerCanWriteToClient() throws Exception{
        System.out.println("Server can write to Client using SocketAutoConnector");
        int sent = 42;
        connector.getServerOut().write(42);
        connector.getServerOut().flush();
        assertEquals(sent, connector.getClientIn().read());
    }
}
