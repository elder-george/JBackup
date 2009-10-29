/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package backupagent;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 *
 * @author Yuri Korchyomkin
 */
public class AgentOptions {
    private String directory;
    private InetAddress serverAddress;
    private int port;

    /**
     * @return the directory
     */
    public String getDirectory() {
        return directory;
    }

    /**
     * @return the serverName
     */
    public InetAddress getServerAddress() {
        return serverAddress;
    }

    /**
     * @return the port
     */
    public int getPort() {
        return port;
    }

    public AgentOptions(String[] args) throws UnknownHostException {
        directory = args[0];
        serverAddress = InetAddress.getByName(args[1]);
        port = Integer.valueOf(args[2]);
    }
}
