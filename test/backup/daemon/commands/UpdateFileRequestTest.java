/*
 */

package backup.daemon.commands;

import backup.daemon.FolderWriter;
import backup.daemon.Session;
import backup.protocol.FileRecord;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Yuri Korchyomkin
 */
public class UpdateFileRequestTest extends Session implements FolderWriter {

    public UpdateFileRequestTest() {
        super(new File("C:\\TMP"), "localhost");
    }

    @Before
    public void setUp() throws Exception {
        this.filename = null;
        this.offset = -1;
        this.bytes = null;
    }

    @After
    public void tearDown() throws Exception {
    }

    /**
     * Test of readAdditionalData method, of class UpdateFileRequest.
     */
    @Test
    public void testReadAdditionalData() throws Exception {
        System.out.println("readAdditionalData");
        byte[] sentBytes = new byte[8192];
        for(int i = 0; i < sentBytes.length; i++)
            sentBytes[i] = (byte)(i % 256);
        InputStream in = new ByteArrayInputStream(sentBytes);
        UpdateFileRequest instance = new UpdateFileRequest(this, "1.txt", 42, sentBytes.length);
        instance.readAdditionalData(in);
    }

    /**
     * Test of process method, of class UpdateFileRequest.
     */
    @Test
    public void testProcess() throws Exception {
        System.out.println("process");
        byte[] sentBytes = new byte[8192];
        for(int i = 0; i < sentBytes.length; i++)
            sentBytes[i] = (byte)(i % 256);
        InputStream in = new ByteArrayInputStream(sentBytes);
        UpdateFileRequest instance = new UpdateFileRequest(this, "1.txt", 42, sentBytes.length);
        instance.readAdditionalData(in);
        instance.process();
        assertEquals("1.txt", this.filename);
        assertEquals(42, this.offset);
        assertArrayEquals(sentBytes, this.bytes);
    }

    public FileRecord[] getStoredFiles() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void deleteFile(String filename) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    String filename;
    int offset;
    byte[] bytes;

    public void updateFile(String filename, int offset, byte[] bytes) {
        this.filename = filename;
        this.offset = offset;
        this.bytes = bytes;
    }


    @Override
    public FolderWriter getFolderWriter(){
        return this;
    }
}