/*
 */

package backup.daemon.commands;

import backup.daemon.FolderWriter;
import backup.daemon.RequestFactory;
import backup.daemon.RequestFactoryImpl;
import backup.daemon.Session;
import backup.protocol.Commands;
import backup.protocol.FileRecord;
import backup.protocol.SocketAutoConnector;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
/**
 *
 * @author Yuri Korchyomkin
 */
public class DeleteFileTest extends Session implements FolderWriter {
    SocketAutoConnector connector;
    RequestFactory factory = new RequestFactoryImpl();
    Session session;

    ArrayList<String> deletedFiles;

    public DeleteFileTest(){
        super("localhost");
    }

    @Before
    public void setUp() throws Exception{
         connector = new SocketAutoConnector();
         session = new Session("localhost");
         deletedFiles = new ArrayList<String>();
    }

    @After
    public void tearDown() throws Exception{
        connector.close();
    }

    @Test
    public void testDeleteFileRequestIsSentAndResponseIsReceived() throws Exception{
       String filename = "1.txt";
       backup.agent.commands.DeleteFileRequest clientRequest = new backup.agent.commands.DeleteFileRequest(filename);
       clientRequest.send(connector.getClientOut());
       
       String requestString = readLine(connector.getServerIn());
       assertTrue(requestString.startsWith(Commands.DELETE_FILE));
       backup.daemon.commands.Request serverRequest = factory.createRequest(this, requestString);
       serverRequest.readAdditionalData(connector.getServerIn());

       backup.daemon.commands.Response serverResponse = serverRequest.process();
       serverResponse.writeResponse(connector.getServerOut());

       String responseString = readLine(connector.getClientIn());
       assertTrue(responseString.startsWith("OK"));
       backup.agent.commands.OKResponse clientResponse = new backup.agent.commands.OKResponse(responseString);
       clientResponse.readAdditionalData(connector.getClientIn());
       assertTrue(deletedFiles.contains(filename));
    }

    private String readLine(InputStream in) throws IOException{
        BufferedReader rdr = new BufferedReader(new InputStreamReader(in));
        return rdr.readLine();
    }

    public FileRecord[] getStoredFiles() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void deleteFile(String filename) {
        deletedFiles.add(filename);
    }

    public void updateFile(String filename, int offset, byte[] bytes) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public FolderWriter getFolderWriter(){
        return this;
    }
}
