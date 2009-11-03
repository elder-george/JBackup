package backup.daemon.commands;

import backup.daemon.Session;
import backup.protocol.Commands;
import backup.protocol.FileRecord;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.util.ArrayList;

/**
 *
 * @author Yuri Korchyomkin
 */
public class GetListRequest extends Request {
    private final Session session;
    private final String directory;
    DateFormat format = DateFormat.getDateTimeInstance();

    public GetListRequest(Session session, String directory) {
        super(Commands.GET_FILE_LIST);
        this.session = session;
        this.directory = directory;
    }

    @Override
    public void readAdditionalData(InputStream in) {
    }

    @Override
    public Response process() {
        FileRecord[] files = session.getFolderWriter().getStoredFiles();
        String[] result = new String[files.length];

        for(int i = 0; i< result.length; i++){
            FileRecord f = files[i];
            result[i] = f.getName() + " " + format.format(f.getModificationDate());
        }
        return new MultilineResponse(result);
    }
}
