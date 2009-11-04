/*
 */

package backup.daemon;

import java.io.File;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Yuri Korchyomkin
 */
public class SessionStoreTest {


    String machineName = "localhost";
    int port = 42;

    /**
     * Test of getSession method, of class SessionStore.
     */
    @Test
    public void testGetSessionReturnsSameVersionsForSameArgs() {
        System.out.println("get session returns same versions for same args");
        SessionStore instance = new SessionStore(new File("."));
        Session expResult = instance.getSession(machineName, port);
        Session result = instance.getSession(machineName, port);
        assertSame(expResult, result);
    }
}