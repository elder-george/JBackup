/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package backup.daemon.commands;

import backup.protocol.Commands;
import backup.protocol.Request;
import backup.protocol.Response;
import java.io.InputStream;

/**
 *
 * @author Yuri Korchyomkin
 * This request should be the first command from agent.
 */
public class SyncDirectoryRequest extends Request {
    private final String directory;

    public SyncDirectoryRequest(String directory) {
        super(Commands.SYNC_DIRECTORY);
        this.directory = directory;
    }

    @Override
    public void readAdditionalData(InputStream in) {
    }

    @Override
    public Response process() {
        return new OKResponse();
    }

}
