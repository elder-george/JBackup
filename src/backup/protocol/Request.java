/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package backup.protocol;

import java.io.InputStream;

/**
 *
 * @author Yuri Korchyomkin
 */
public abstract class Request {
    protected String commandText;

    protected Request(String commandText){
        this.commandText = commandText;
    }

    public abstract void readAdditionalData(InputStream in);

    public abstract Response process();
}
