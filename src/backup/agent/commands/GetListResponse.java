/*
 */

package backup.agent.commands;

import backup.protocol.FileRecord;
import backup.protocol.Responses;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;

/**
 * Client-side response for GetListRequest.
 * Typical response looks like:
 * "OK|2"
 * "1.txt|02.03.2009 22:00:00"
 * @author Yuri Korchyomkin
 */
public class GetListResponse implements Response{

    Integer lineCount;
    DateFormat format = DateFormat.getDateTimeInstance();
    FileRecord[] files;

    public GetListResponse(String responseString){
        String[] parts = responseString.split("\\|");
        if(!parts[0].equals(Responses.OK))
            throw new RuntimeException("Request processing failed: "+ parts[1]);
        lineCount = Integer.valueOf(parts[1]);
    }

    String readLine(java.io.InputStream in) throws IOException{
        int symbol;
        StringBuffer buffer = new StringBuffer();
        while((symbol = in.read()) != -1){
            if(symbol == '\n' || symbol == '\r' || symbol == Character.LINE_SEPARATOR)
                break;
            buffer.append((char) symbol);
        }
        return buffer.toString();
    }

    /**
     * reconstructs list of FileRecord objects from received data.
     * @param in stream to read data from
     * @throws IOException
     * @throws ParseException
     */
    @Override
    public void readAdditionalData(InputStream in) throws IOException, ParseException {
        assert(files == null);
        ArrayList<FileRecord> fileRecords = new ArrayList<FileRecord>();
        for(int i = 0; i < lineCount; i++)
        {
            String line = readLine(in);
            String[] record = line.split("\\|");
            fileRecords.add(new FileRecord(record[0], format.parse(record[1])));
        }
        this.files = new FileRecord[fileRecords.size()];
        fileRecords.toArray(files);
    }

    public FileRecord[] getFiles(){
        assert(files != null);
        return files;
    }
}
