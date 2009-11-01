package backup.agent;

import java.util.Calendar;
import java.util.Date;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Yuri Korchyomkin
 */
public class FileRecordTest {

    public FileRecordTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    Calendar calendar = Calendar.getInstance();

    /**
     * Test of getModificationDate method, of class FileRecord.
     */
    @Test
    public void testGetModificationDate() {
        System.out.println("getModificationDate");
        Date expResult = calendar.getTime();
        FileRecord instance = new FileRecord("1.txt", expResult);
        Date result = instance.getModificationDate();
        assertEquals(expResult, result);
    }

    /**
     * Test of getName method, of class FileRecord.
     */
    @Test
    public void testGetName() {
        System.out.println("getName");
        String expResult = "1.txt";
        FileRecord instance = new FileRecord(expResult, calendar.getTime());
        String result = instance.getName();
        assertEquals(expResult, result);
    }

    /**
     * Test of hashCode method, of class FileRecord.
     */
    @Test
    public void testHashCode() {
        System.out.println("hashCode");
        Date date = calendar.getTime();

        FileRecord instance1 = new FileRecord("1.txt", date);
        FileRecord instance2 = new FileRecord("1.txt", date);

        assertEquals(instance1.hashCode(), instance2.hashCode());
    }

    /**
     * Test of equals method, of class FileRecord.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        Date date = calendar.getTime();

        FileRecord instance1 = new FileRecord("1.txt", date);
        FileRecord instance2 = new FileRecord("1.txt", date);

        assertEquals(instance1, instance2);
    }

    @Test
    public void testEqualsIgnoresModificationTime(){
        System.out.println("testEqualsIgnoresModificationTime");
        Date date1 = calendar.getTime();
        calendar.add(Calendar.HOUR, 1);
        Date date2 = calendar.getTime();
        FileRecord instance1 = new FileRecord("1.txt", date1);
        FileRecord instance2 = new FileRecord("1.txt", date2);
        assertFalse(date1.equals(date2));
        assertEquals(instance1, instance2);

    }

}