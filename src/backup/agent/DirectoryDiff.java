package backup.agent;

import backup.protocol.FileRecord;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;

/**
 * Takes two lists of files and sort them into several lists:
 * - list of created in second list (exists in second but misses in first);
 * - list of deleted from second list (exists in first but misses in second);
 * - list of changed (basing on files modification dates);
 * - list of non-changed.
 *
 * @author Yuri Korchyomkin
 */
public class DirectoryDiff {
    /**
     * helper class comparing instances of FileRecord. Compares them by names.
     */
    class FileRecordComparator implements Comparator<FileRecord>{
        Collator innerComparator = Collator.getInstance();
        public int compare(FileRecord o1, FileRecord o2) {
            return innerComparator.compare(o1.getName(), o2.getName());
        }
    }

    final Comparator<FileRecord> comparator = new FileRecordComparator();

    Collection<FileRecord> changed = new ArrayList<FileRecord>();
    Collection<FileRecord> notChanged = new ArrayList<FileRecord>();
    Collection<FileRecord> created = new ArrayList<FileRecord>();
    Collection<FileRecord> deleted = new ArrayList<FileRecord>();

    /**
     * Sorts files into lists of created, deleted, changed and not changed.
     * @param monitoredFiles - list of files stored on server.
     * @param currentFiles - list of actual files that need be stored.
     */
    public DirectoryDiff(FileRecord[] monitoredFiles, FileRecord[] currentFiles){
        Arrays.sort(currentFiles, comparator);
        Arrays.sort(monitoredFiles, comparator);

        int idxCurrent = 0;
        int idxMonitored = 0;
        // iterating over lists.
        while(idxCurrent < currentFiles.length || idxMonitored < monitoredFiles.length)
        {
            // we need to delete file if reached end of currentFiles
            //  or if current item of currentFiles is 'later' then current item of modifiedFiles
            boolean needDelete = idxCurrent == currentFiles.length 
                    || (idxMonitored < monitoredFiles.length && comparator.compare(currentFiles[idxCurrent], monitoredFiles[idxMonitored])>0);
            // we need to delete file if reached end of monitoredFiles
            //  or if current item of currentFiles is 'earlier' then current item of modifiedFiles
            boolean needCreate = idxMonitored == monitoredFiles.length ||
                    (idxCurrent < currentFiles.length && comparator.compare(currentFiles[idxCurrent], monitoredFiles[idxMonitored]) < 0);
            if(needDelete){ // there's no matching file in current version
                deleted.add(monitoredFiles[idxMonitored]);
                idxMonitored++;
            }else if(needCreate){ // there's no matching file in server's version
                created.add(currentFiles[idxCurrent]);
                idxCurrent++;
            }else{ // there're files with the same name that can differ in versions
                if(currentFiles[idxCurrent].getModificationDate()
                        .after(monitoredFiles[idxMonitored].getModificationDate())){
                    changed.add(currentFiles[idxCurrent]);
                }
                else{
                    notChanged.add(currentFiles[idxCurrent]);
                }
                idxCurrent++;
                idxMonitored++;
            }
        }
    }

    public Collection<FileRecord> getDeleted(){
        return deleted;
    }

    public Collection<FileRecord> getChanged(){
        return changed;
    }

    public Collection<FileRecord> getCreated(){
        return created;
    }

    public Collection<FileRecord> getNotChanged(){
        return notChanged;
    }
}
