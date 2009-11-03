/*
 */

package backup.agent.commands;

import backup.protocol.FileRecord;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.text.DateFormat;
import java.util.Calendar;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Yuri Korchyomkin
 */
public class GetListResponseTest {

    public GetListResponseTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    Calendar calendar = Calendar.getInstance();
    DateFormat format = DateFormat.getDateTimeInstance();
    /**
     * Test of readAdditionalData method, of class GetListResponse.
     */
    @Test
    public void testReadAdditionalData() throws Exception {
        System.out.println("readAdditionalData");
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        BufferedWriter wr = new BufferedWriter(new OutputStreamWriter(out));
        wr.write("1.txt "+ format.format(calendar.getTime())+"\n");
        wr.write("2.txt "+format.format(calendar.getTime())+"\n");
        wr.flush();
        InputStream in = new ByteArrayInputStream(out.toByteArray());
        GetListResponse instance = new GetListResponse("OK "+2);
        instance.readAdditionalData(in);
    }

    /**
     * Test of getFiles method, of class GetListResponse.
     */
    @Test
    public void testGetFiles() throws Exception{
        System.out.println("getFiles");
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        BufferedWriter wr = new BufferedWriter(new OutputStreamWriter(out));
        wr.write("1.txt "+ format.format(calendar.getTime())+"\n");
        wr.write("2.txt "+format.format(calendar.getTime())+"\n");
        wr.flush();
        InputStream in = new ByteArrayInputStream(out.toByteArray());
        GetListResponse instance = new GetListResponse("OK "+2);
        instance.readAdditionalData(in);
        assertEquals("1.txt", instance.getFiles()[0].getName());
        assertEquals("2.txt", instance.getFiles()[1].getName());
    }

}