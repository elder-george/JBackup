package backup.daemon;

import java.io.IOException;
import java.net.*;
import java.util.concurrent.Executor;

/**
 *
 * @author Yuri Korchyomkin
 */
public class TCPBackupServer implements Runnable {
    private final SessionStore sessions;
    private final ServerSocket listeningSocket;
    private final Thread thread;
    private final Executor executor;
    boolean stopped;
    private final RequestFactoryImpl requestFactory;

    public TCPBackupServer(SessionStore sessions, ServerSocket listeningSocket, Executor executor,
                            RequestFactoryImpl requestFactory){
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
        Session session = this.sessions.getSession(socket.getInetAddress().getCanonicalHostName(), 
                                                    socket.getPort());
        executor.execute(new TCPHandler(session, requestFactory, socket.getInputStream(), socket.getOutputStream()));
    }
}
