package backup.daemon;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Provides sessions.
 * @author Yuri Korchyomkin
 */
public class SessionStore {
    private final File rootDirectory;
    /**
     * Helper object used as key for sessions dictionary.
     * Sessions are searched by 'address'-'port' key.
     */
    class SessionDescriptor{
        final String machineName;
        final Integer port;

        public SessionDescriptor(String machineName, int port){
            this.machineName = machineName;
            this.port = Integer.valueOf(port);
        }

        @Override
        public int hashCode(){
            return machineName.hashCode() ^ port.hashCode();
        }

        @Override
        public boolean equals(Object obj){
            if(obj == null || !(obj instanceof SessionDescriptor))
                return false;
            SessionDescriptor other = (SessionDescriptor)obj;
            return other.machineName.equals(other.machineName)
                    && other.port.equals(other.port);
        }
    }

    // Note that sessions leak after client disconnection.
    // This can be solved by using WeakHashMap but it is not tested.
    Map<SessionDescriptor, Session> sessions = new HashMap<SessionDescriptor, Session>();

    public SessionStore(File rootDirectory){
        this.rootDirectory = rootDirectory;
    }

    /**
     * Returns session for given machine name (address) and port.
     * Session will be created if necessary.
     * @param machineName name of machine.
     * @param port port
     * @return session.
     */
    public synchronized Session getSession(String machineName, int port){
        SessionDescriptor key = new SessionDescriptor(machineName, port);
        Session session = sessions.get(key);
        if(session == null){
            session = new Session(rootDirectory, machineName);
            sessions.put(key, session);
        }
        File sessionDirectory = new File(rootDirectory, machineName);
        if(!sessionDirectory.exists())
            sessionDirectory.mkdir();
        return session;
    }
}
