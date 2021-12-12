package softmeth.android.models;

import java.io.File;
import java.io.FileInputStream;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * Represents a Photo. Each Photo has a name, caption, and a list
 * of Tags. 
 * Contains mostly protected methods
 * to prevent accidental data manipulation.
 * @author <em>kma224</em> - Kayla Alvarado
 * @author <em>ey150</em>  - Ernest Yakobchuk
 */
public class Photo implements Serializable {
    private String         path;
    private String         caption;
    private ArrayList<Tag> tags;

    /**
     * Constructor of a Photo instance given only the path of
     * the photo. Caption and tags default to empty
     * @param path path of the image file
     */
    public Photo(String path)
    {
        this.path     = path;
        this.caption  = null;
        this.tags     = new ArrayList<Tag>();
    }
    
    
    /** 
     * Gets the path of this photo
     * @return the path as a String
     */
    protected String getPath()
    {
        return this.path.toString();
    }

    /** 
     * Sets the caption of this photo
     * @param caption desired caption for the photo
     */
    protected void setCaption(String caption)
    {
        this.caption = caption;
    }

    /** 
     * Gets the caption of this photo
    * @return the caption of this photo as a String
     */
    protected String getCaption()
    {
        return this.caption;
    }

    /** 
     * Gets the list of tags of this photo
     * @return ArrayList of {@link Tag} objects associated
     * with this photo
     */
    protected ArrayList<Tag> getTags()
    {
        return this.tags;
    }

    /** 
     * @return {@code true} if this photo and the passed Object
     * are both Photo objects with the same path, {@code false}
     * otherwise
     */
    @Override
    public boolean equals(Object o)
    {
        if (o == null)
            return false;
        if (o instanceof Photo)
        {
            Photo p = (Photo) o;
            if (p.path.equals(this.path))
                return true;
        }
        return false;
    }

    protected String getLastModifiedDate()
    {
        File          file = new File(path);
        LocalDateTime date = LocalDateTime.ofInstant(Instant.ofEpochMilli(file.lastModified()), ZoneId.systemDefault());
        
        DateTimeFormatter format = DateTimeFormatter.ofPattern("MMM dd YYYY hh:mm a");

        return date.format(format);
    }

    protected LocalDateTime getLastModifiedDateAsTime()
    {
        File          file = new File(path);
        LocalDateTime date = LocalDateTime.ofInstant(Instant.ofEpochMilli(file.lastModified()), ZoneId.systemDefault());
        
        return date;
    }

    protected FileInputStream getStream()
    {
        String path = this.path;
        try
        {
            FileInputStream is = new FileInputStream(path);
            return is;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }
}
