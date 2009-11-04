package backup.daemon.commands;

import backup.daemon.Session;
import java.io.File;
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
        Session session = new Session(new File("C:\\TMP"),"localhost");
        String dir = "TMP";
        SyncDirectoryRequest instance = new SyncDirectoryRequest(session,dir);
        instance.process();
        assertEquals(dir, session.getDirectory());
    }

}