package backup.daemon;

import backup.agent.commands.SyncDirectoryRequest;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Yuri Korchyomkin
 */
public class TCPBackupServerTest {
    static final int PORT = 4242;
    TCPBackupServer server;
    SessionStore sessions;
    ServerSocket listener;
    File rootDirectory;
    ExecutorService executor;
    @Before
    public void setUp() throws Exception{
        rootDirectory = new File("daemon-test");
        assertTrue(rootDirectory.mkdir());
        sessions = new SessionStore();
        executor = Executors.newCachedThreadPool();
        listener = new ServerSocket(PORT);
        server = new TCPBackupServer(sessions, listener, executor, new RequestFactoryImpl());
    }

    @After
    public void tearDown() throws Exception {
        if(server != null) server.stop();
        cleanDirectory(rootDirectory);
        assertFalse(rootDirectory.exists());
        listener.close();
        executor.shutdown();
    }

    /**
     * Test of run method, of class TCPBackupServer.
     */
    @Test
    public void testRun() throws Exception{
        System.out.println("run");
        runServer(100);
    }

    @Test
    public void testTCPBackupServerAcceptsConnections() throws Exception{
        System.out.println("TCPBackupServer accepts connections");
        server.start();
        Socket clientConnection = new Socket("127.0.0.1", PORT);
        assertTrue(clientConnection.isConnected());
        Thread.sleep(200);
        clientConnection.close();
    }

    @Test
    public void testTCPBackupHandlesSimpleRequests() throws Exception{
        System.out.println("TCPBackupServer handles simple requests connections");
        server.start();
        Socket clientConnection = new Socket("127.0.0.1", PORT);
        assertTrue(clientConnection.isConnected());

        SyncDirectoryRequest request = new SyncDirectoryRequest("C:/TMP");
        request.send(clientConnection.getOutputStream());

        BufferedReader rdr = new BufferedReader(new InputStreamReader(clientConnection.getInputStream()));
        System.out.println(rdr.readLine());
        clientConnection.close();
    }


    void runServer(long time) throws Exception{
        server.start();
        Thread.sleep(time);
    }

    private void cleanDirectory(File directory) throws Exception{
        for(File f: directory.listFiles()) {
            if(f.isFile())
                f.delete();
            else
                cleanDirectory(f);
        }
        directory.delete();
    }

}