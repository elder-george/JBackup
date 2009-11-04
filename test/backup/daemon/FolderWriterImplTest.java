package backup.daemon;

import backup.protocol.FileRecord;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
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
public class FolderWriterImplTest {

    public FolderWriterImplTest() {
    }

    File folder;

    ArrayList<File> files = new ArrayList<File>();

    @Before
    public void setUp() throws Exception {
        folder = new File("test-directory");
        boolean result = folder.mkdir();
        assert(result);
        for(int i = 0; i < 10; i++){
            File f = new File(folder, Integer.valueOf(i).toString());
            f.createNewFile();
            files.add(f);
        }
    }

    @After
    public void tearDown() throws Exception {
        for(File f: folder.listFiles())
            if(f != null)
                f.delete();
        folder.delete();
        files.clear();
    }

    /**
     * Test of getStoredFiles method, of class FolderWriterImpl.
     */
    @Test
    public void testGetStoredFiles() {
        System.out.println("getStoredFiles");
        FolderWriterImpl instance = new FolderWriterImpl(folder);
        FileRecord[] expResult = new FileRecord[files.size()];
        for(int i = 0; i < files.size(); i++)
            expResult[i] = new FileRecord(files.get(i).getName(), new Date(files.get(i).lastModified()));
        FileRecord[] result = instance.getStoredFiles();
        assertArrayEquals(expResult, result);
    }

    /**
     * Test of deleteFile method, of class FolderWriterImpl.
     */
    @Test
    public void testDeleteFile() throws Exception {
        System.out.println("deleteFile");
        String filename = files.get(files.size() - 2).getName();
        FolderWriterImpl instance = new FolderWriterImpl(folder);
        instance.deleteFile(filename);
        for(File f : folder.listFiles()){
            assertFalse(f.getName().equals(filename));
        }
    }

    /**
     * Test of updateFile method, of class FolderWriterImpl.
     */
    @Test
    public void testUpdateFile() throws Exception {
        System.out.println("updateFile");
        String filename = files.get(0).getName();
        int offset = 0;
        byte[] writtenBytes = new byte[1024];
        for(int i = 0; i < writtenBytes.length; i++)
            writtenBytes[i] = (byte)(i % 256);
        FolderWriterImpl instance = new FolderWriterImpl(folder);
        instance.updateFile(filename, offset, writtenBytes);

        FileInputStream in = new FileInputStream(files.get(0));
        int count = 0;
        try{
            int readValue;
            while((readValue = in.read()) != -1){
                assertEquals((byte)readValue, writtenBytes[count]);
                count++;
            }
            assertEquals(writtenBytes.length, count);
        }finally{
            in.close();
        }
    }

}