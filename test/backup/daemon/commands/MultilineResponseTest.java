package backup.daemon.commands;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;
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

    /**
     * Test of writeResponse method, of class MultilineResponse.
     */
    @Test
    public void testWriteResponse() throws Exception {
        System.out.println("writeResponse");
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        String[] lines = new String[]{ "1.txt", "2.txt"};
        MultilineResponse instance = new MultilineResponse(lines);
        instance.writeResponse(out);
        ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
        BufferedReader rdr = new BufferedReader(new InputStreamReader(in));
        String[] header = rdr.readLine().split("\\|");
        assertEquals(header[0], "OK");
        int lineNumber = Integer.valueOf(header[1]);
        assertEquals(lines.length, lineNumber);
        for(int i = 0; i < lineNumber; i++){
            assertEquals(lines[i], rdr.readLine());
        }
    }

}