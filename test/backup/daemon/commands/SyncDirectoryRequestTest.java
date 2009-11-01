package backup.daemon.commands;

import backup.protocol.Response;
import java.io.InputStream;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Yuri Korchyomkin
 */
public class SyncDirectoryRequestTest {

    public SyncDirectoryRequestTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    /**
     * Test of readAdditionalData method, of class SyncDirectoryRequest.
     */
    @Test
    public void testReadAdditionalData() {
        System.out.println("readAdditionalData");
        InputStream in = null;
        SyncDirectoryRequest instance = null;
        instance.readAdditionalData(in);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of process method, of class SyncDirectoryRequest.
     */
    @Test
    public void testProcess() {
        System.out.println("process");
        SyncDirectoryRequest instance = null;
        Response expResult = null;
        Response result = instance.process();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}