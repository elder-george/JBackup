/*
 */

package backup.agent.commands;

import backup.protocol.Commands;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Yuri Korchyomkin
 */
public class UpdateFileRequestTest {

    public UpdateFileRequestTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    String readLine(java.io.InputStream in) throws IOException{
        int symbol;
        StringBuffer buffer = new StringBuffer();
        while((symbol = in.read()) != -1){
            if(symbol == '\n' || symbol == '\r' || symbol == Character.LINE_SEPARATOR)
                break;
            buffer.append((char) symbol);
        }
        return buffer.toString();
    }

    /**
     * Test of send method, of class UpdateFileRequest.
     */
    @Test
    public void testSend() throws Exception {
        System.out.println("send");
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] bytes = new byte[]{1,2,3,4,5,6,7,8,9,10};
        int offset = 42;
        String filename = "1.txt";
        UpdateFileRequest instance = new UpdateFileRequest(filename, offset, bytes);
        instance.send(out);
        ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
        String[] requestStringParts = readLine(in).split(" ");
        assertEquals(Commands.UPDATE_FILE, requestStringParts[0]);
        assertEquals(filename, requestStringParts[1]);
        int actualOffset = Integer.valueOf(requestStringParts[2]);
        int size = Integer.valueOf(requestStringParts[3]);
        assertEquals(offset, actualOffset);
        assertEquals(bytes.length, size);
        byte[] actualBytes = new byte[size];
        assertEquals(size, in.read(actualBytes));
        assertArrayEquals(bytes, actualBytes);
    }

}