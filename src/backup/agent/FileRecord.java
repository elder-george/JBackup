package backup.agent;

import java.util.Date;

/**
 *
 * @author Yuri Korchyomkin
 */
public class FileRecord {
    final private Date modificationDate;
    final private String name;

    public FileRecord( String name, Date modificationDate) {
        this.modificationDate = modificationDate;
        this.name = name;
    }

    /**
     * @return the modificationDate
     */
    public Date getModificationDate() {
        return modificationDate;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    @Override
    public int hashCode(){
        return name.hashCode();
    }

    @Override
    public boolean equals(Object obj){
        if(obj == this)
            return true;
        if(obj == null || !(obj instanceof FileRecord))
            return false;
        final FileRecord other = (FileRecord)obj;
        return this.getName().equals(other.getName());
    }
}
