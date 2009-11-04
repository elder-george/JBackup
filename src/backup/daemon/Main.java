package backup.daemon;

import java.io.IOException;
import java.net.ServerSocket;
import java.text.ParseException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author Yuri Korchyomkin
 */
public class Main {
    public static void main(String[] args){
        TCPBackupServer server = null;
        try{
            DaemonOptions options = new DaemonOptions(args);
            SessionStore sessionStore = new SessionStore();
            ServerSocket socket = new ServerSocket(options.getPort());
            ExecutorService executor = Executors.newCachedThreadPool();
            RequestFactory requestFactory = new RequestFactoryImpl();
            server = new TCPBackupServer(sessionStore, socket, executor, requestFactory);
            server.start();
        }catch(ParseException ex){
            System.err.println(ex.getMessage());
        }
        catch(IOException ex){
            System.err.println("Network exchange error: "+ ex.getMessage());
        }
        finally{
            if(server != null)
                server.stop();
        }
    }
}
