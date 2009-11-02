package backup.daemon;

import backup.daemon.commands.Request;

/**
 *
 * @author Yuri Korchyomkin
 */
public interface RequestFactory {

    Request createRequest(Session session, String string);

}
