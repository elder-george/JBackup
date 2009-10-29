/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package backupagent;


import java.text.Collator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;

/**
 *
 * @author Yuri Korchyomkin
 */
public class DirectoryDiff {
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

    public DirectoryDiff(FileRecord[] monitoredFiles, FileRecord[] currentFiles){
        Arrays.sort(currentFiles, comparator);
        Arrays.sort(monitoredFiles, comparator);

        int idxCurrent = 0;
        int idxMonitored = 0;
        while(idxCurrent < currentFiles.length || idxMonitored < monitoredFiles.length)
        {
            boolean needDelete = idxCurrent == currentFiles.length 
                    || (idxMonitored < monitoredFiles.length && comparator.compare(currentFiles[idxCurrent], monitoredFiles[idxMonitored])>0);
            //int cmpResult = comparator.compare(currentFiles[idxCurrent], monitoredFiles[idxMonitored]);
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
                        .equals(monitoredFiles[idxMonitored].getModificationDate())){
                    notChanged.add(currentFiles[idxCurrent]);
                }
                else{
                    changed.add(currentFiles[idxCurrent]);
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
