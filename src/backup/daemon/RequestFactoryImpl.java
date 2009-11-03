package backup.daemon;

import backup.daemon.commands.DeleteFileRequest;
import backup.daemon.commands.GetListRequest;
import backup.daemon.commands.SyncDirectoryRequest;
import backup.daemon.commands.Request;
import backup.daemon.commands.UpdateFileRequest;
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
            return new GetListRequest(session, session.getDirectoryName());
        if(parts[0].equals(Commands.DELETE_FILE))
            return new DeleteFileRequest(session, parts[1]);
        if(parts[0].equals(Commands.UPDATE_FILE))
            return new UpdateFileRequest(session, parts[1], Integer.valueOf(parts[2]), Integer.valueOf(parts[3]));
        throw new IllegalArgumentException(string);
    }
}
