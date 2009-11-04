package backup.daemon;

import java.io.IOException;
import java.net.*;
import java.util.concurrent.Executor;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Server listening for incoming connections and spawning working threads for handling them.
 * @author Yuri Korchyomkin
 */
public class TCPBackupServer implements Runnable {
    private final SessionStore sessions;
    private final ServerSocket listeningSocket;
    private final Thread thread;
    private final Executor executor;
    boolean stopped;
    private final RequestFactory requestFactory;

    /**
     * Initializes instance of TCPBackupServer.
     * @param sessions session provider.
     * @param listeningSocket socket on which connections will come.
     * @param executor object used for spawning handlers.
     * @param requestFactory requests parser.
     */
    public TCPBackupServer(SessionStore sessions, ServerSocket listeningSocket, Executor executor,
                            RequestFactory requestFactory){
        this.sessions = sessions;
        this.listeningSocket = listeningSocket;
        this.thread = new Thread(this, "listener");
        this.executor = executor;
        this.requestFactory = requestFactory;
    }

    public void start(){
        thread.start();
    }

    public void run() {
        System.out.println("Listening started");
        // we spawn a handler for each accepted connection.
        while(!stopped){
            try{
                spawnHandler(listeningSocket.accept());
            }catch(IOException ex){
                System.err.println("Can't start handler: " + ex.getMessage());
            }
        }
    }

    /**
     * Signals listening thread to stop and interrupts it if it hasn't stopped in time.
     */
    public void stop(){
        try{
            stopped = true;
            try {
                listeningSocket.close();
            } catch (IOException ex) {
                // we are ready to consequences.
            }
            thread.join(1000);
            thread.interrupt();
        }catch(InterruptedException ex){
            System.err.println("Thread was interrupted");
        }
    }

    private void spawnHandler(Socket socket) throws IOException {
        System.out.println("Started work with client at "+ socket.getInetAddress()+":"+socket.getPort());
        Session session = this.sessions.getSession(socket.getInetAddress().getCanonicalHostName(), 
                                                    socket.getPort());
        executor.execute(new TCPHandler(session, requestFactory, socket.getInputStream(), socket.getOutputStream()));
    }
}
