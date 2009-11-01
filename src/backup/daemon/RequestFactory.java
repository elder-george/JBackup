package backup.daemon;

import backup.protocol.Request;

/**
 *
 * @author Yuri Korchyomkin
 */
public interface RequestFactory {

    Request createRequest(Session session, String string);

}
