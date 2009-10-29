/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package backupagent;

import java.net.InetAddress;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Yuri Korchyomkin
 */
public class AgentOptionsTest {

    public AgentOptionsTest() {
    }

    @Before
    public void setUp() throws Exception{
        int port=4242;
        String directory = "c:\\TMP";
        defaultArgs = new String[]{
                                "c:\\TMP",
                                "127.0.0.1",
                                "4242"
                            };
    }



    String[] defaultArgs;
    /**
     * Test of getDirectory method, of class AgentOptions.
     */
    @Test
    public void testGetDirectory() throws Exception {
        System.out.println("getDirectory");
        AgentOptions instance = new AgentOptions(defaultArgs);
        String expResult = defaultArgs[0];
        String result = instance.getDirectory();
        assertEquals(expResult, result);
    }

    /**
     * Test of getServerAddress method, of class AgentOptions.
     */
    @Test
    public void testGetServerName() throws Exception{
        System.out.println("getServerName");
        AgentOptions instance = new AgentOptions(defaultArgs);
        InetAddress expResult = InetAddress.getByName(defaultArgs[1]);
        InetAddress result = instance.getServerAddress();
        assertEquals(expResult, result);
    }

    /**
     * Test of getPort method, of class AgentOptions.
     */
    @Test
    public void testGetPort() throws Exception {
        System.out.println("getPort");
        AgentOptions instance = new AgentOptions(defaultArgs);
        int expResult = Integer.valueOf(defaultArgs[2]);
        int result = instance.getPort();
        assertEquals(expResult, result);
    }

}