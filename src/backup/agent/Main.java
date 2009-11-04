package backup.agent;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

/**
 *
 * @author Yuri Korchyomkin
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {

        Socket socket = null;
        Monitor monitor = null;
        try{
            AgentOptions options = new AgentOptions(args);
            socket = new Socket(options.getServerAddress(), options.getPort());
            BackupService service = new BackupServiceImpl(socket.getOutputStream(), socket.getInputStream());
            System.out.println("connecting to "+options.getServerAddress()+":"+options.getPort());
            monitor = new Monitor(new FolderImpl(options.getDirectory()),service, 5000);
            monitor.start();
            System.in.read();
        }finally{
            if(monitor != null)
                monitor.stop();
            if(socket != null)
                socket.close();
        }
    }
}
