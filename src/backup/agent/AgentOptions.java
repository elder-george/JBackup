package backup.agent;

import java.net.InetAddress;
import java.text.ParseException;

/**
 *
 * @author Yuri Korchyomkin
 *
 * Options parser for client application.
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

    /**
     * Initialize AgentOptions object from command line aruments.
     * @param args arguments received in function main
     * @throws ParseException if arguments are incorrect
     */
    public AgentOptions(String[] args) throws ParseException {
        try{
            directory = args[0];
            serverAddress = InetAddress.getByName(args[1]);
            port = Integer.valueOf(args[2]);
        }catch(Throwable ex){
            throw new ParseException("Error while parsing arguments.\n"+
                    "Usage: BackupAgent <directory> <host address> <port>", -1);
        }
    }
}
