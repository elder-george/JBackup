package backup.daemon;

import backup.daemon.commands.GetListRequest;
import backup.daemon.commands.SyncDirectoryRequest;
import backup.protocol.Request;
import backup.protocol.Commands;

/**
 *
 * @author Yuri Korchyomkin
 */
public class RequestFactoryImpl implements RequestFactory {
    public Request createRequest(Session session, String string){
        String[] parts = string.split(" ");
        assert(parts.length > 0);
        if(parts[0].equals(Commands.SYNC_DIRECTORY)){
            return new SyncDirectoryRequest(session, parts[1]);
        }
        if(parts[0].equals(Commands.GET_FILE_LIST))
            return new GetListRequest(session.getDirectoryName());
        throw new IllegalArgumentException(string);
    }
}
