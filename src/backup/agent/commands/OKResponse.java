package backup.agent.commands;

import backup.protocol.Responses;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;

/**
 * Client-side object corresponding to response without additional data.
 * Throws exception if actual response means error.
 * @author Yuri Korchyomkin
 */
public class OKResponse implements Response {

    /**
     * Initializes response object.
     * If response text means error, throws RuntimeException.
     * @param responseText text of response.
     */
    public OKResponse(String responseText){
        if(responseText.startsWith(Responses.ERROR))
        {
            String[] parts = responseText.split("\\|");
            throw new RuntimeException(parts[1]);
        }else if(!responseText.startsWith(Responses.OK))
            throw new RuntimeException("Unknown response: "+responseText);
    }
    @Override
    public void readAdditionalData(InputStream in) throws IOException, ParseException {
    }
}
