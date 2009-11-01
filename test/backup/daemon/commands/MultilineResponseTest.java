/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package backup.daemon.commands;

import java.io.OutputStream;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Yuri Korchyomkin
 */
public class MultilineResponseTest {

    public MultilineResponseTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    /**
     * Test of writeResponse method, of class MultilineResponse.
     */
    @Test
    public void testWriteResponse() {
        System.out.println("writeResponse");
        OutputStream out = null;
        MultilineResponse instance = null;
        instance.writeResponse(out);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}