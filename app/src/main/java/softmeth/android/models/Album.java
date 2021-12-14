package softmeth.android.models;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Represents an Album. Each Album has a name, earliest modification date,
 * latest modification date, and a list of photos. 
 * Contains mostly protected methods
 * to prevent accidental data manipulation.
 * @author <em>kma224</em> - Kayla Alvarado
 * @author <em>ey150</em>  - Ernest Yakobchuk
 */
public class Album implements Serializable {
    private String                name;
    private ArrayList<Photo>      photos;

    /**
     * Constructs an Album instance. Sets the earliest and latest 
     * date/time to the current date/time.
     * @param name      name of the album
     * @param photos    list of photos to add to this album
     */
    public Album(String name, ArrayList<Photo> photos)
    {
        this.name     = name;
        this.photos   = new ArrayList<Photo>();
    }

    /**
     * Constructs an Album instance with no photos. 
     * Sets the earliest and latest 
     * date/time to the current date/time.
     * @param name      name of the album
     */
    public Album(String name)
    {
        this.name     = name;
        this.photos   = new ArrayList<Photo>();
    }

    /** 
     * Gets this album's number of photos.
     * @return number of photos in the album as an integer
     */
    public int getNumPhotos()
    {
        if (this.photos == null)
            return 0;
        else
            return this.photos.size();
    }

    /** 
     * Get the name of this album
     * @return name of this album
     */
    public String getName()
    {
        return this.name;
    }

    /** 
     * Gets this album's list of photos
     * @return ArrayList of {@link Photo} objects in this album
     */
    public ArrayList<Photo> getPhotos()
    {
        return this.photos;
    }
    
    /** 
     * Gets {@link Photo} from this album
     * @param photo photo to search for
     * @return reference to the photo, or null if it was not found
     */
    public Photo getPhoto(Photo photo)
    {
        if (this.photos.contains(photo))
        {
            int index = this.photos.indexOf(photo);

            return this.photos.get(index);
        }
        return null;
    }

    protected Photo getPhoto(int index)
    {
        if (this.photos.size() <= index)
            return null;
        return this.photos.get(index);
    }

    /** 
     * Adds a photo to this album's list of photos. Duplicates 
     * are not allowed, and this album's {@code latest} date will be changed
     * to the date/time of the addition.
     * @param photo {@link Photo} instance to be added
     * @return {@code true} if photo was successfully added,
     * {@code false} if not.
     */
    public boolean addPhoto(Photo photo)
    {
        if (photo == null)
            return false;
        if (this.photos.contains(photo))
            return false;
        // Add photo to album
        this.photos.add(photo);

        return true;
    }

    /** 
     * @return {@code true} if this and o are both Albums and
     * have the same username, false otherwise
     */
    @Override
    public boolean equals(Object o)
    {
        if (o == null)
            return false;
        if (o instanceof Album)
        {
            Album a = (Album) o;
            return this.name.equals(a.name);
        }
        return false;
    }   

    /** 
     * @return String in the format {@code <album name> | <number of
     * photos>}
     */
    @Override
    public String toString()
    {
        if (this.getNumPhotos() == 0)
            return name + " | No photos";
        return name + " | Photos: " + getNumPhotos();
    }

    /**
     * Rename this album to newName
     * @param newName   the new name of the album
     */
    public void setName(String newName) {
        this.name = newName;
    }

    
    /** 
     * Deletes a photo from this album
     * @param photo photo to be deleted
     * @return {@code true} if photo was successfully deleted, 
     * {@code false} if not
     */
    public boolean deletePhoto(Photo photo) {
            this.photos.remove(photo);
            return true;
    }
}