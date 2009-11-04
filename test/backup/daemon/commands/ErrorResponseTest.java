/*
 */

package backup.daemon.commands;

import backup.protocol.Responses;
import java.io.ByteArrayOutputStream;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Yuri Korchyomkin
 */
public class ErrorResponseTest {

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of writeResponse method, of class ErrorResponse.
     */
    @Test
    public void testErrorResponseHandlesMultilineStringsCorrectly() throws Exception {
        System.out.println("ErrorResponse handles multiline strings correctly");
        StringBuffer buffer = new StringBuffer();
        buffer.append( "Poor Yorick!\r");
        buffer.append("I knew him well,\n");
        buffer.append("Horatio!\r\n");
        String message = buffer.toString();
        ErrorResponse response = new ErrorResponse(message);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        response.writeResponse(out);
        String s = out.toString();
        assertTrue(s.startsWith(Responses.ERROR));
        int count = 0;
        for(int i = 0; i< s.length(); i++){
            char c = s.charAt(i);
            if(c == '\r' || c == '\n' || c == Character.LINE_SEPARATOR)
                count++;
        }
        assertEquals(1, count);
    }

}