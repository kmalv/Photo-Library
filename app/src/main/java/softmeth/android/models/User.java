package softmeth.android.models;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Represents a User. Each User has a username, a list of albums,
 * a list of photos associated with them, and a list of all tags
 * associated with them as well.
 * Contains mostly protected methods
 * to prevent accidental data manipulation.
 * @author <em>kma224</em> - Kayla Alvarado
 * @author <em>ey150</em>  - Ernest Yakobchuk
 */
public class User implements Serializable{
    private ArrayList<Album> albums;
    private ArrayList<Photo> photoBank;
    
    /**
     * Constructs a User instance. Other
     * fields are set to empty by default.
     */
    public User()
    {
        this.albums    = new ArrayList<Album>();
        this.photoBank = new ArrayList<Photo>();
    }

    /** 
     * Get the list of albums associated with this user.
     * @return ArrayList of {@link Album} objects associated with
     * this user.
     */
    public ArrayList<Album> getAlbums()
    {
        return this.albums;
    }

    /** 
     * Get a single {@link Album} from this user's library,
     * given an {@link Album}
     * @param album album to get from the user's library
     * @return {@link Album} instance as it exists in the user's library.
     * Could return null if album doesn't exist.
     */
    public Album getAlbum(Album album)
    {
        int index = this.albums.indexOf(album);
        if (index == -1)
            return null;
        return this.albums.get(index);
    }

    public Album getAlbum(int index)
    {
        if (albums == null)
            return null;
        if (albums.isEmpty())
            return null;
        return albums.get(index);
    }

    protected boolean deleteAlbum(int index)
    {
        Album toDelete = getAlbum(index);
        if (toDelete == null)
            return false;
        albums.remove(index);
        return true;
    }

    /**
     * Get list of photos associated with this user, regardless
     * of their album 
     * @return ArrayList of {@link Photo} objects associated with
     * this user
     */
    public ArrayList<Photo> getPhotoBank()
    {
        return this.photoBank;
    }

    /** 
     * Add a photo to this user's list of photos. This method 
     * checks if photo exists in the {@code photoBank} before adding it.
     * @param photo {@link Photo} to add
     * @return {@code true} if photo was successfully added,
     * {@code false} otherwise. 
     */
    public boolean addPhotoToPhotoBank(Photo photo)
    {
        if (photo == null)
            return false;
        if (photoBank.contains(photo))
            return false;
        photoBank.add(photo);
        return true;
    }
    
    /** 
     * Add an album to the user's list of albums. The list is 
     * checked for existing copies before adding.
     * @param album {@link Album} object to add to user's list
     * @return {@code true} if album was added, {@code false}
     * otherwise
     */
    public boolean addAlbum(Album album) {
        if (albums.contains(album))
            return false;
        else {
            albums.add(album);
            return true;
        }
    }
}
