/*
 */

package backup.protocol;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author Yuri Korchyomkin
 */
public class SocketAutoConnector implements Closeable {

    ServerSocket listener;
    Socket clientSide;
    Socket serverSide;
    ExecutorService executor;

    public SocketAutoConnector() throws Exception{
        executor = Executors.newSingleThreadExecutor();
        listener = new ServerSocket();
        listener.bind(new InetSocketAddress(4242));
        Future<Socket> futureServerSide = executor.submit(new Callable<Socket>(){

            public Socket call() throws Exception {
                return listener.accept();
            }

        });
        clientSide = new Socket("localhost", 4242);
        serverSide = futureServerSide.get(1, TimeUnit.SECONDS);
    }

    public InputStream getServerIn() throws IOException{
        return serverSide.getInputStream();
    }
    public OutputStream getServerOut() throws IOException{
        return serverSide.getOutputStream();
    }

    public InputStream getClientIn() throws IOException{
        return clientSide.getInputStream();
    }
    public OutputStream getClientOut() throws IOException{
        return clientSide.getOutputStream();
    }

    public void close() throws IOException {
        executor.shutdown();
        clientSide.close();
        serverSide.close();
        listener.close();
    }

}
