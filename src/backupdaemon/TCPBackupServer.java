package backupdaemon;

import java.io.IOException;
import java.net.*;
import java.util.concurrent.Executor;

/**
 *
 * @author Yuri Korchyomkin
 */
public class TCPBackupServer implements Runnable {
    private final ServerSocket listeningSocket;
    private final Thread thread;
    private final Executor executor;
    boolean stopped;

    public TCPBackupServer(ServerSocket listeningSocket, Executor executor){
        this.listeningSocket = listeningSocket;
        this.thread = new Thread(this, "listener");
        this.executor = executor;
    }

    public void start(){
        thread.start();
    }

    public void run() {
        while(!stopped){
            try{
                spawnHandler(listeningSocket.accept());
            }catch(IOException ex){
                System.err.println("Can't start handler: " + ex.getMessage());
            }
        }
    }

    public void stop(){
        try{
            stopped = true;
            thread.join();
        }catch(InterruptedException ex){
            System.err.println("Thread was interrupted");
        }
    }

    private void spawnHandler(Socket socket) throws IOException {
            executor.execute(new TCPHandler(socket.getInputStream(), socket.getOutputStream()));
    }
}
