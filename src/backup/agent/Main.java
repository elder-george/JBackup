package backup.agent;

import java.io.IOException;
import java.net.Socket;
import java.text.ParseException;

/**
 *
 * @author Yuri Korchyomkin
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException{

        Socket socket = null;
        Monitor monitor = null;
        try{
            AgentOptions options = new AgentOptions(args);
            socket = new Socket(options.getServerAddress(), options.getPort());
            BackupService service = new BackupServiceImpl(socket.getOutputStream(), socket.getInputStream());
            System.out.println("connecting to "+options.getServerAddress()+":"+options.getPort());
            monitor = new Monitor(new FolderReaderImpl(options.getDirectory()),service, 2000);
            monitor.start();
            System.in.read();
        }
        catch(ParseException ex){
            System.err.println(ex.getMessage());
        }
        catch(IOException ex){
            System.err.println("I/O error: "+ex.getMessage());
        }
        finally{
            if(monitor != null)
                monitor.stop();
            if(socket != null)
                socket.close();
        }
    }
}
