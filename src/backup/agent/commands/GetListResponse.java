/*
 */

package backup.agent.commands;

import backup.agent.FileRecord;
import backup.protocol.Responses;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Yuri Korchyomkin
 */
public class GetListResponse implements Response{

    Integer lineCount;
    DateFormat format = DateFormat.getDateTimeInstance();
    FileRecord[] files;

    public GetListResponse(String responseString){
        String[] parts = responseString.split(" ");
        if(!parts[0].equals(Responses.OK))
            throw new RuntimeException("Request processing failed: "+ parts[1]);
        lineCount = Integer.valueOf(parts[1]);
    }

    @Override
    public void readAdditionalData(InputStream in) throws IOException, ParseException {
        assert(files == null);
        ArrayList<FileRecord> fileRecords = new ArrayList<FileRecord>();
        BufferedReader rdr = new BufferedReader(new InputStreamReader(in));
        for(int i = 0; i < lineCount; i++)
        {
            String[] record = rdr.readLine().split(" ");
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
