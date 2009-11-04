package backup.daemon;

import backup.daemon.commands.Request;
import backup.daemon.commands.Response;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.SocketException;

/**
 * Object handling requests from single agent.
 * @author Yuri Korchyomkin
 */
public class TCPHandler implements Runnable {
    private final Session session;
    private final InputStream in;
    private final OutputStream out;
    final static int MAX_RECV_BUFFER = 8192;
    private final RequestFactory requestFactory;

    TCPHandler(Session session, backup.daemon.RequestFactory requestFactory, InputStream in, OutputStream out){
        this.session = session;
        this.in = in;
        this.out = out;
        this.requestFactory = requestFactory;
    }

    public void run() {
        Request request;
        try{
            while(true){
                // read a line with request header.
                String command = readCommand();
                if(command == null || command.equals(""))
                    continue;
                // parse it
                request = requestFactory.createRequest(session, command);
                // some requests require additional data (currently, only UPD_FILE)
                request.readAdditionalData(in);
                // as a result of processing we receive response object
                Response response = request.process();
                // that can be sent to client.
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
            ex.printStackTrace(System.err);
        }
    }

    private String readCommand() throws SocketException, IOException {
        StringBuffer buffer = new StringBuffer();
        int symbol;
        try{
            while((symbol = in.read()) != -1){
                if(symbol == '\n' || symbol == '\r' || symbol == Character.LINE_SEPARATOR)
                    break;
                buffer.append((char)symbol);
            }
        }
        finally{
            return buffer.toString();
        }
    }
}
