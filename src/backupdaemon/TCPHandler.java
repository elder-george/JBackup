/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package backupdaemon;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.SocketException;

/**
 *
 * @author Yuri Korchyomkin
 */
public class TCPHandler implements Runnable {
    private final InputStream in;
    private final OutputStream out;
    final static int MAX_RECV_BUFFER = 8192;

    TCPHandler(InputStream in, OutputStream out){
        this.in = in;
        this.out = out;
    }

    public void run() {
        String command;
        byte[] data = new byte[MAX_RECV_BUFFER];
        try{
            while(true){
                command = readCommand();
                if(hasAdditionalData(command));
                    readAdditionalData(data);
                 handleCommand(command, data);
            }
        }catch(SocketException ex){
            System.err.println("Connection broken : "+ ex.getMessage());
            return;
        }
        catch(IOException ex){
            System.err.println("I/O error: "+ex.getLocalizedMessage());
        }
    }

    private String readCommand() throws SocketException, IOException {
        StringBuffer buffer = new StringBuffer();
        int symbol;
        while(-1 != (symbol = in.read())){
            if(Character.isValidCodePoint(symbol)
                    || symbol != Character.LINE_SEPARATOR)
                buffer.append(Character.toChars(symbol));
            else break;
        }
        return buffer.toString();
    }

    private boolean hasAdditionalData(String command) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private void readAdditionalData(byte[] data) throws IOException  {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private void handleCommand(String command, byte[] data)  throws IOException {
        throw new UnsupportedOperationException("Not yet implemented");
    }

}
