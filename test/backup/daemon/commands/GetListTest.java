package backup.daemon.commands;

import backup.agent.commands.GetListResponse;
import backup.daemon.FolderWriter;
import backup.daemon.RequestFactory;
import backup.daemon.RequestFactoryImpl;
import backup.daemon.Session;
import backup.protocol.Commands;
import backup.protocol.FileRecord;
import backup.protocol.SocketAutoConnector;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Calendar;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Yuri Korchyomkin
 */
public class GetListTest extends Session implements FolderWriter{

    SocketAutoConnector connector;
    RequestFactory factory = new RequestFactoryImpl();
    FileRecord[] files;

    public GetListTest(){
        super(new File("c:\\TMP"),"localhost");
    }

    @Before
    public void setUp() throws Exception {
        connector = new SocketAutoConnector();
    }

    @After
    public void tearDown() throws Exception {
        connector.close();
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


    @Test
    public void testMultilineResponseFromServerCanBeParsedAsGetListResponse() throws Exception{
        System.out.println("MultilineResponse from server can be parsed as GetListResponse");
        Calendar c = Calendar.getInstance();
        DateFormat f = DateFormat.getDateTimeInstance();
        MultilineResponse serverResponse = new MultilineResponse(new String[]{
                    "1.txt|"+f.format(c.getTime()),
                    "2.txt|"+f.format(c.getTime()),
                });
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        serverResponse.writeResponse(out);
        ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
        String responseString = readLine(in);
        GetListResponse clientResponse = new GetListResponse(responseString);
        clientResponse.readAdditionalData(in);
        assertEquals("1.txt", clientResponse.getFiles()[0].getName());
        assertEquals("2.txt", clientResponse.getFiles()[1].getName());
    }

    /**
     * Test of process method, of class GetListRequest.
     */
    @Test
    public void testProcess() throws Exception{
        System.out.println("process");
        Calendar c = Calendar.getInstance();
        files = new FileRecord[]{   new FileRecord("1.txt", c.getTime()),
                                    new FileRecord("яяя.txt", c.getTime())};
        // sending request to server
        backup.agent.commands.GetListRequest clientSideRequest = new backup.agent.commands.GetListRequest();
        clientSideRequest.send(connector.getClientOut());
        // receiving request on server
        String requestString = readLine(connector.getServerIn());
        System.out.println(requestString);
        assertTrue(requestString.startsWith(Commands.GET_FILE_LIST));
        Request serverSideRequest = factory.createRequest(this, requestString);
        serverSideRequest.readAdditionalData(connector.getServerIn());
        // processing request and writing response to client
        serverSideRequest.process().writeResponse(connector.getServerOut());
        // receiving response on client
        String responseString = readLine(connector.getClientIn());
        backup.agent.commands.GetListResponse clientSideResponse = new GetListResponse(responseString);
        clientSideResponse.readAdditionalData(connector.getClientIn());

        assertArrayEquals(clientSideResponse.getFiles(), files);
    }

    public FileRecord[] getStoredFiles() {
        return files;
    }

    public void deleteFile(String filename) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void updateFile(String filename, int offset, byte[] bytes) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public FolderWriter getFolderWriter(){
        return this;
    }
}