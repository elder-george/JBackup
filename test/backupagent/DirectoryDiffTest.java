/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package backupagent;

import java.util.*;
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
public class DirectoryDiffTest {

    public DirectoryDiffTest() {
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


    static<E> Set<E> toSet(E[] items){
        return new HashSet(Arrays.asList(items));
    }

    Calendar calendar = Calendar.getInstance();

    /**
     * Test of getDeleted method, of class DirectoryDiff.
     */
    @Test
    public void testGetDeleted() {
        System.out.println("getDeleted");
        Date date = calendar.getTime();
        FileRecord[] monitoredFiles = new FileRecord[]{new FileRecord("1",date),
        new FileRecord("2", date), new FileRecord("3", date)};
        FileRecord[] actualFiles = new FileRecord[]{new FileRecord("1",date),
        new FileRecord("2",date)};
        DirectoryDiff instance = new DirectoryDiff(monitoredFiles, actualFiles);
        List<FileRecord> expResult = Collections.singletonList(new FileRecord("3", date));
        List<FileRecord> result = (List<FileRecord>)instance.getDeleted();
        assertArrayEquals(expResult.toArray(), result.toArray());
    }

    /**
     * Test of getChanged method, of class DirectoryDiff.
     */
    @Test
    public void testGetChanged() {
        System.out.println("getChanged");
        Date date1 = calendar.getTime();
        calendar.add(Calendar.HOUR, 1);
        Date date2 = calendar.getTime();
        FileRecord[] monitored = new FileRecord[]{ new FileRecord("1", date1)};
        FileRecord[] actual = new FileRecord[]{ new FileRecord("1", date2)};
        DirectoryDiff instance = new DirectoryDiff(monitored, actual);
        
        List<FileRecord> result = (List<FileRecord>)instance.getChanged();
        assertArrayEquals(actual, result.toArray());
    }

    /**
     * Test of getCreated method, of class DirectoryDiff.
     */
    @Test
    public void testGetCreated() {
        System.out.println("getCreated");
        DirectoryDiff instance = new DirectoryDiff(new FileRecord[0],
                new FileRecord[]{ new FileRecord("1", calendar.getTime())});
        List<FileRecord> expResult = Collections.singletonList(new FileRecord("1", calendar.getTime()));
        List<FileRecord> result = (List<FileRecord>)instance.getCreated();
        assertArrayEquals(expResult.toArray(), result.toArray());
    }

    /**
     * Test of getNotChanged method, of class DirectoryDiff.
     */
    @Test
    public void testGetNotChanged() {
        System.out.println("getNotChanged");
        Date date1 = calendar.getTime();
        calendar.add(Calendar.HOUR, 1);
        Date date2 = calendar.getTime();
        FileRecord[] monitored = new FileRecord[]{
            new FileRecord("tochange", date1),
            new FileRecord("todelete", date1),
            new FileRecord("tonotchange", date1)
        };
        FileRecord[] current = new FileRecord[]{
            new FileRecord("tochange", date2),
            new FileRecord("tonotchange", date1),
            new FileRecord("tocreate", date2)
        };
        DirectoryDiff instance = new DirectoryDiff(monitored, current);
        List<FileRecord> expResult = Collections.singletonList(new FileRecord("tonotchange", date1));
        List<FileRecord> result = (List<FileRecord>)instance.getNotChanged();
        assertArrayEquals(expResult.toArray(), result.toArray());
    }

    @Test
    public void testResultsComplementEachOther(){
        System.out.println("Results complement each other");
        Date date1 = calendar.getTime();
        calendar.add(Calendar.HOUR, 1);
        Date date2 = calendar.getTime();
        FileRecord[] monitored = new FileRecord[]{
            new FileRecord("tochange", date1),
            new FileRecord("todelete", date1),
            new FileRecord("tonotchange", date1)
        };
        FileRecord[] current = new FileRecord[]{
            new FileRecord("tochange", date2),
            new FileRecord("tonotchange", date1),
            new FileRecord("tocreate", date2)
        };
        DirectoryDiff instance = new DirectoryDiff(monitored, current);

        Set<FileRecord> notChangedOfMonitored = new HashSet<FileRecord>(Arrays.asList(monitored));
        notChangedOfMonitored.removeAll(instance.getDeleted());
        notChangedOfMonitored.removeAll(instance.getChanged());
        assertArrayEquals(notChangedOfMonitored.toArray(), instance.getNotChanged().toArray());

        Set<FileRecord> notChangedOfCurrent = new HashSet<FileRecord>(Arrays.asList(current));
        notChangedOfCurrent.removeAll(instance.getCreated());
        notChangedOfCurrent.removeAll(instance.getChanged());
        assertArrayEquals(notChangedOfCurrent.toArray(), instance.getNotChanged().toArray());
    }

    @Test
    public void testUpdatedFileShouldNotBeMarkedAsDeleted(){
        System.out.println("Updated files shouldn't be marked as deleted");
        FileRecord[] monitoredFiles = new FileRecord[]{
                new FileRecord("1", calendar.getTime()),
                new FileRecord("2", calendar.getTime()),
                new FileRecord("tochange", calendar.getTime())
        };
        Calendar calendar2 = Calendar.getInstance();
        calendar2.add(Calendar.HOUR, 1);
        FileRecord[] actualFiles = new FileRecord[]{
                new FileRecord("1", calendar.getTime()),
                new FileRecord("2", calendar.getTime()),
                new FileRecord("tochange", calendar2.getTime())
        };
        DirectoryDiff diff = new DirectoryDiff(monitoredFiles, actualFiles);
        assertEquals(1, diff.getChanged().size());
        assertEquals(0, diff.getCreated().size());
        assertEquals(0, diff.getDeleted().size());
    }
}