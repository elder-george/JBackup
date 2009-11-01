package backup.daemon;

import backup.protocol.Request;
import backup.protocol.Response;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.SocketException;
import java.nio.ByteBuffer;

/**
 *
 * @author Yuri Korchyomkin
 */
public class TCPHandler implements Runnable {
    private final Session session;
    private final InputStream in;
    private final OutputStream out;
    final static int MAX_RECV_BUFFER = 8192;
    private final RequestFactoryImpl requestFactory;

    TCPHandler(Session session, backup.daemon.RequestFactoryImpl requestFactory, InputStream in, OutputStream out){
        this.session = session;
        this.in = in;
        this.out = out;
        this.requestFactory = requestFactory;
    }

    public void run() {
        Request request;
        try{
            while(true){
                String command = readCommand();
                request = requestFactory.createRequest(session, command);
                request.readAdditionalData(in);
                Response response = request.process();
                response.writeResponse(out);
            }
        }catch(SocketException ex){
            System.err.println("Connection broken : "+ ex.getMessage());
            return;
        }
        catch(IOException ex){
            System.err.println("I/O error: "+ex.getLocalizedMessage());
        }
        catch(Exception ex){
            System.err.println("Unexpected error: " + ex.toString());
        }
    }

    private String readCommand() throws SocketException, IOException {
        DataInput di = new DataInputStream(in);
        StringBuffer buffer = new StringBuffer();
        char symbol;
        try{
            while(true){
                symbol = di.readChar();
                if(symbol == '\n' || symbol == '\r' || symbol == Character.LINE_SEPARATOR)
                    break;
                buffer.append(symbol);
            }
        }
        finally{
            return buffer.toString();
        }
    }
}
