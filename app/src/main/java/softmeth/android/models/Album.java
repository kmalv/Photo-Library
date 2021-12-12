package softmeth.android.models;

import java.io.Serializable;
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
    private String        name;
    ArrayList<Photo>      photos;

    /**
     * Constructs an Album instance. Sets the earliest and latest 
     * date/time to the current date/time.
     * @param name      name of the album
     * @param photos    list of photos to add to this album
     */
    public Album(String name, ArrayList<Photo> photos)
    {
        this.name     = name;
        this.photos   = photos;
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
    protected int getNumPhotos() 
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
    protected String getName() 
    {
        return this.name;
    }

    /**
     * Gets the date of the photo with the earliest
     * modification date in the album
     * @return {@code earliest} date in MMM dd YYYY format
     */
    protected String getEarliestDateString() 
    {
        List<LocalDateTime> list = this.getDatesOfAllPhotos();
        if (list == null)
            return "(null)";
        if (list.size() < 0)
            return "(null)";
        DateTimeFormatter format = DateTimeFormatter.ofPattern("MMM dd YYYY");

        return list.get(0).format(format);
    }

    /**
     * Gets the date of the photo with the latest
     * modification date in the album
     * @return {@code latest} date in MMM dd YYYY format
     */
    protected String getLatestDateString() 
    {
        List<LocalDateTime> list = this.getDatesOfAllPhotos();
        if (list == null)
            return "null";
        if (list.size() < 0)
            return "null";
        DateTimeFormatter format = DateTimeFormatter.ofPattern("MMM dd YYYY");

        return list.get(list.size() -1).format(format);
    }

    
    /** 
     * Gets the LocalDateTime instances of all photos
     * in this album
     * @return List<LocalDateTime>
     */
    private List<LocalDateTime> getDatesOfAllPhotos()
    {
        List<LocalDateTime> dates = this.photos
                                        .stream()
                                        .map(Photo::getLastModifiedDateAsTime)
                                        .sorted()
                                        .collect(Collectors.toList());
        
        return dates;
    }

    /** 
     * Gets this album's list of photos
     * @return ArrayList of {@link Photo} objects in this album
     */
    protected ArrayList<Photo> getPhotos()
    {
        return this.photos;
    }
    
    /** 
     * Gets {@link Photo} from this album
     * @param photo photo to search for
     * @return reference to the photo, or null if it was not found
     */
    protected Photo getPhoto(Photo photo)
    {
        if (photos.contains(photo))
        {
            int index = photos.indexOf(photo);

            return photos.get(index);
        }
        return null;
    }

    /** 
     * Adds a photo to this album's list of photos. Duplicates 
     * are not allowed, and this album's {@code latest} date will be changed
     * to the date/time of the addition.
     * @param photo {@link Photo} instance to be added
     * @return {@code true} if photo was successfully added,
     * {@code false} if not.
     */
    protected boolean addPhoto(Photo photo) 
    {
        if (photo == null)
            return false;
        if (photos.contains(photo))
            return false;
        // Add photo to album and change this album's
        // most recently modified date value
        photos.add(photo);

        return true;
    }
    
    /** 
     * Adds an ArrayList of phots to this album's list of photos. Duplicates 
     * are not allowed, and this album's {@code latest} date will be changed
     * to the date/time of the addition.
     * @param photo {@link Photo} instance to be added
     * @return {@code true} if <em>all</em> photos were successfully added,
     * {@code false} if not.
     */
    protected boolean addPhoto(ArrayList<Photo> photosToAdd)
    {
        if (photosToAdd == null)
            return false;
        // Check if album has a photo in photosToAdd already
        boolean hasDuplicate = false;    
        for (Photo p : photos)
        {
            if (photosToAdd.contains(p))
            {
                hasDuplicate = true;
                break;
            }
        }

        if (hasDuplicate)
            return false;
        else
        {
            photos.addAll(photosToAdd);

            return true;
        }
    }

    /** 
     * @return {@code true} if this and o are both Albums and
     * have the same username, false otherwise
     */
    @Override
    public boolean equals(Object o)
    {
        if (o instanceof Album)
        {
            Album a = (Album) o;
            return this.name.equals(a.name);
        }
        return false;
    }   

    /** 
     * @return String in the format {@code <album name> | <number of
     * photos> | <earliest date in MMM dd YYYY format> | <latest
     * date in MMM dd YYYY format>}
     */
    @Override
    public String toString()
    {
        if (this.getNumPhotos() == 0)
            return name + " | No photos";
        return name + " | Photos: " + getNumPhotos() + " | " + this.getEarliestDateString() + " | " + this.getLatestDateString();
    }

    /**
     * Rename this album to newName
     * @param newName   the new name of the album
     */
    protected void setName(String newName) {
        this.name = newName;
    }

    
    /** 
     * Deletes a photo from this album
     * @param photo photo to be deleted
     * @return {@code true} if photo was successfully deleted, 
     * {@code false} if not
     */
    protected boolean deletePhoto(Photo photo) {
            photos.remove(photo);
            return true;
    }
}