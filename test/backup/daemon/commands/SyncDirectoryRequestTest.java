package backup.daemon.commands;

import backup.daemon.Session;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Yuri Korchyomkin
 */
public class SyncDirectoryRequestTest {

    /**
     * Test of process method, of class SyncDirectoryRequest.
     */
    @Test
    public void testProcessSetsDirectoryInSession() {
        System.out.println("SyncDirectoryRequestTest sets directory in session");
        Session session = new Session("localhost");
        String dir = "C:/TMP";
        SyncDirectoryRequest instance = new SyncDirectoryRequest(session,dir);
        instance.process();
        assertEquals(dir, session.getDirectoryName());
    }

}