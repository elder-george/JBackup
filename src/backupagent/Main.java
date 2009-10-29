package backupagent;

import java.io.IOException;

/**
 *
 * @author Yuri Korchyomkin
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        AgentOptions options = new AgentOptions(args);
        BackupService service = connectToDaemon(options);
        System.out.println("connecting to "+options.getServerAddress()+":"+options.getPort());
        Monitor monitor = new Monitor(options.getDirectory(),service);
        monitor.start();
        System.in.read();
        monitor.stop();
    }

    private static BackupService connectToDaemon(AgentOptions options) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

}
