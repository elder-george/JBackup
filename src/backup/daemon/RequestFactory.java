package backup.daemon;

import backup.daemon.commands.Request;

/**
 * Interface of requests factory.
 * Implementations should create requests from given header string using Session if necessary.
 * @author Yuri Korchyomkin
 */
public interface RequestFactory {
    /**
     * Creates a request from given string.
     * @param session session object associated with current connection.
     * @param requestString string that should be parsed
     * @return created request. 
     */
    Request createRequest(Session session, String requestString);

}
