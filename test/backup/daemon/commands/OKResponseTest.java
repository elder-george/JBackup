package backup.daemon.commands;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Yuri Korchyomkin
 */
public class OKResponseTest {

    public OKResponseTest() {
    }

    /**
     * Test of writeResponse method, of class OKResponse.
     */
    @Test
    public void testWriteResponse() throws Exception {
        System.out.println("OKResponse writes response");
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        
        OKResponse instance = new OKResponse();
        instance.writeResponse(out);

        ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
        BufferedReader rdr = new BufferedReader(new InputStreamReader(in));
        String responseText = rdr.readLine();
        assertTrue(responseText.equals("OK"));
   }
}