package backup.daemon;

import backup.protocol.Commands;
import backup.daemon.commands.Request;
import java.io.*;
import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Yuri Korchyomkin
 */
public class TCPHandlerTest extends RequestFactoryImpl{
    private ExecutorService executor;
    private OutputStreamWriter pipeOut;
    private PipedInputStream pipeIn;

    @Before
    public void handlerOut() throws IOException {

        session = new Session(new File("C:\\TMP"),"localhost");

        in = new PipedInputStream();
        pipeOut = new OutputStreamWriter(new PipedOutputStream(in));
        out = new PipedOutputStream();
        pipeIn = new PipedInputStream(out);
        handler = new TCPHandler(session, this, in, out);
        executor = Executors.newCachedThreadPool();

        requests = new ArrayList<String>();
        requestsCounter = new Semaphore(0);

        executor.submit(handler);
    }

    @After
    public void tearDown() {
        executor.shutdown();
    }

    @Override
    public Request createRequest(Session session, String command){
        requests.add(command);
        requestsCounter.release();
        return super.createRequest(session, command);
    }

    Session session;
    PipedInputStream in;
    PipedOutputStream out;
    TCPHandler handler;

    ArrayList<String> requests;
    Semaphore requestsCounter;
    /**
     * Test of run method, of class TCPHandler.
     */
    @Test
    public void testCanHandleSingleRequest() throws Exception {
        System.out.println("Can handle single request");
        pipeOut.write(Commands.SYNC_DIRECTORY + " hello" + "\n");
        waitProcessing(1);
        assertEquals(1, requests.size());
        assertTrue(requests.get(0).startsWith(Commands.SYNC_DIRECTORY));
    }

    String readLineFromPipe() throws IOException{
        StringBuffer sb = new StringBuffer();
        int symbol;
        while((symbol = pipeIn.read()) != -1){
            if(symbol == '\n' || symbol == '\r' || symbol == Character.LINE_SEPARATOR)
                    break;
            sb.append((char) symbol);
        }
        return sb.toString();
    }

    @Test
    public void testResponseIsWrittenToOutputStream() throws Exception{
        System.out.println("Response is written to output stream");
        pipeOut.write(Commands.SYNC_DIRECTORY + " hello" + "\n");
        waitProcessing(1);
        CharBuffer buf = CharBuffer.allocate(1024);
        String response = readLineFromPipe();
        assertTrue(response.startsWith("OK"));
    }

    @Test
    public void testCanHandleMultipleRequests() throws Exception{
        System.out.println("Can handle multiple requests");
        pipeOut.write(Commands.SYNC_DIRECTORY + " hello" + "\n");
        pipeOut.write(Commands.GET_FILE_LIST + "\n");
        waitProcessing(2);
        assertEquals(2, requests.size());
        assertTrue(requests.get(0).startsWith(Commands.SYNC_DIRECTORY));
        assertTrue(requests.get(1).startsWith(Commands.GET_FILE_LIST));                
    }

    private void waitProcessing(int requestsCount) throws Exception {
        if(! requestsCounter.tryAcquire(requestsCount, 1000*requestsCount, TimeUnit.MILLISECONDS))
            throw new IllegalStateException("request was not processed in time");
    }

}