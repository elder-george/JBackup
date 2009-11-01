package backup.daemon;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Yuri Korchyomkin
 */
public class SessionStore {
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

    Map<SessionDescriptor, Session> sessions = new HashMap<SessionDescriptor, Session>();

    public SessionStore(){
    }

    public synchronized Session getSession(String machineName, int port){
        SessionDescriptor key = new SessionDescriptor(machineName, port);
        Session session = sessions.get(key);
        if(session == null){
            session = new Session(machineName);
            sessions.put(key, session);
        }
        return session;
    }
}
