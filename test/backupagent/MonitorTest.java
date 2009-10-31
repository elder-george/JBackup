/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package backupagent;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Yuri Korchyomkin
 */
public class MonitorTest implements BackupService, Folder {

    public MonitorTest() {
    }

    @Before
    public void setUp() {
        actualFiles = new ArrayList<FileRecord>();
        monitoredFiles = new ArrayList<FileRecord>();
        deleted = new ArrayList<String>();
        created = new ArrayList<String>();
        updated = new HashMap<String, Integer>();
    }

    @After
    public void tearDown() {
    }

    List<FileRecord> actualFiles;
    List<FileRecord> monitoredFiles;
    List<String> deleted;
    List<String> created;
    Map<String, Integer> updated;

    Calendar calendar = Calendar.getInstance();
    /**
     * Test of start method, of class Monitor.
     */
    @Test
    public void testInitialFilesAddition() throws Exception {
        System.out.println("Initial files addition");
        monitoredFiles = new ArrayList<FileRecord>();
        actualFiles = Arrays.asList(new FileRecord[]{ 
            new FileRecord("1", calendar.getTime()),
            new FileRecord("2", calendar.getTime())
        });
        runMonitor();
        assertArrayEquals(actualFiles.toArray(), monitoredFiles.toArray());
    }

    @Test
    public void testFilesDelete() throws Exception {
        System.out.println("Deleting files");
        monitoredFiles = new ArrayList<FileRecord>(Arrays.asList(
                new FileRecord("1", calendar.getTime()),
                new FileRecord("todelete", calendar.getTime()),
                new FileRecord("2", calendar.getTime())
        ));
        actualFiles = Arrays.asList(
                new FileRecord("1", calendar.getTime()),
                new FileRecord("2", calendar.getTime())
        );
        runMonitor();
        assertArrayEquals(actualFiles.toArray(), monitoredFiles.toArray());
    }

    @Test
    public void testUpdatingFiles() throws Exception{
        System.out.println("Updating files");
        monitoredFiles = new ArrayList<FileRecord>(Arrays.asList(
                new FileRecord("1", calendar.getTime()),
                new FileRecord("2", calendar.getTime()),
                new FileRecord("tochange", calendar.getTime())
        ));
        Calendar calendar2 = Calendar.getInstance();
        calendar2.add(Calendar.HOUR, 1);
        actualFiles = Arrays.asList(
                new FileRecord("1", calendar.getTime()),
                new FileRecord("2", calendar.getTime()),
                new FileRecord("tochange", calendar2.getTime())
        );
        runMonitor();
        assertEquals(actualFiles.size(), monitoredFiles.size());
        for(int i =0; i< actualFiles.size(); i++)
        {
            assertEquals(actualFiles.get(i).getName(), actualFiles.get(i).getName());
            assertEquals(actualFiles.get(i).getModificationDate(), actualFiles.get(i).getModificationDate());
        }
    }

    public FileRecord[] getMonitoredFilesList() {
        FileRecord[] records = new FileRecord[monitoredFiles.size()];
        return monitoredFiles.toArray(records);
    }

    public void deleteFile(String fileName) {
        deleted.add(fileName);
        monitoredFiles.remove(new FileRecord(fileName, null));
        System.out.printf("file %s was deleted\n", fileName);
    }

    public void updateFile(String name, Date modificationDate, int offset, byte[] contents) {
        //Integer oldValue = updated.get(name);
        if(!updated.containsKey(name)){
            created.add(name);
            updated.put(name, contents.length);
            monitoredFiles.add(new FileRecord(name, modificationDate));
        }
        else
            updated.put(name, updated.get(name).intValue() + contents.length);
        System.out.printf("file %s was updated\n", name);
    }

    public String getName() {
        return "mock_folder";
    }

    public FileRecord[] getFiles() {
        FileRecord[] records = new FileRecord[actualFiles.size()];
        return actualFiles.toArray(records);
    }

    public InputStream openFile(FileRecord file) throws IOException {
        return new ByteArrayInputStream(new byte[1]);
    }

    private void runMonitor() throws InterruptedException {
        Monitor instance = new Monitor(this, this, 50);
        try {
            instance.start();
            Thread.sleep(200);
        } finally {
            instance.stop();
        }
    }
}