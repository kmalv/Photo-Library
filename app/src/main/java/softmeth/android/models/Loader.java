package softmeth.android.models;

import android.content.Context;
import android.graphics.Bitmap;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class Loader {
    private static final String filename = "info.data";
    private static Context context;
    private static User user = null;
    private static ArrayList<Photo> searchResults = new ArrayList<Photo>();

    public enum Search { OR, AND };

    // Loads user info from info.data file
    public static void loadUser(Context passedContext)
    {
        context = passedContext;
        try
        {
            FileInputStream in = context.openFileInput(filename);
            ObjectInputStream objIn = new ObjectInputStream(in);

            user = (User) objIn.readObject();

            in.close();
            objIn.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        if (user == null)
        {
            user = new User();
        }
    }

    private static void saveUser()
    {
        try
        {
            FileOutputStream out = context.openFileOutput(filename, Context.MODE_PRIVATE);
            ObjectOutputStream objOut = new ObjectOutputStream(out);

            objOut.writeObject(user);

            out.close();
            objOut.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public static User getUser()
    {
        return user;
    }

    public static boolean deleteAlbum(int index)
    {
        if (user == null)
            return false;
        if (user.getAlbum(index) == null)
            return false;
        else
        {
            // If photo only exists in the album to be deleted,
            // we need to delete it
            for (Photo p : user.getAlbum(index).getPhotos())
            {
                boolean hasDupes = false;

                // Check every album for if it has the photo
                for (Album a : user.getAlbums())
                {
                    if (a.getPhotos().contains(p))
                        hasDupes = true;
                    break;
                }
                // Photo does not exist in any other album
                if (!hasDupes)
                    user.getPhotoBank().remove(p);
            }

            // Finally, delete the album and save the changes
            user.getAlbums().remove(user.getAlbum(index));
            saveUser();
            return true;
        }
    }

    public static boolean deletePhotoFromAlbum(int albumIndex, int photoIndex)
    {
        // first make sure the photo's even in the album
        if (user.getAlbum(albumIndex) != null)
        {
            Album album = user.getAlbum(albumIndex);
            // Then check that photo exists in the album
            if (album.getPhoto(photoIndex) != null)
            {
                Photo photo = album.getPhoto(photoIndex);

                user.getPhotoBank().remove(photo);
                user.getAlbum(albumIndex).getPhotos().remove(photo);
                saveUser();
                return true;
            }
        }
        return false;
    }

    public static boolean renameAlbum(int index, String newName)
    {
        if (user == null)
            return false;
        Album album = user.getAlbum(index);
        // Prevent duplicates
        if (album != null)
        {
            if (user.getAlbums().contains(new Album((newName))))
                return false;
            album.setName(newName);
            saveUser();
            return true;
        }
        return false;
    }

    public static boolean movePhoto(int albumIndex, int photoIndex, int newalbumindex)
    {
        if (user.getAlbum(albumIndex) == null)
            return false;
        if (user.getAlbum(albumIndex).getPhoto(photoIndex) == null)
            return false;
        //check that album/photo exist in the user's list of albums/photos
        if (user.getAlbum(albumIndex).getPhoto(photoIndex) != null){
            if (user.getAlbum(newalbumindex) != null) {
                Photo temp = user.getAlbum(albumIndex).getPhoto(photoIndex);
                deletePhotoFromAlbum(albumIndex, photoIndex);

                if(user.getAlbum(newalbumindex).addPhoto(temp)){
                    saveUser();
                    return true;
                }
            }
        } return false;


    }

    public static Album getAlbum(int index)
    {
        return user.getAlbum(index);
    }

    public static ArrayList<Photo> getPhotosFromAlbum(int index)
    {
        Album album = user.getAlbum(index);
        if (album != null)
            return album.getPhotos();
        return null;
    }

    public static ArrayList<Album> getAlbums()
    {
        return user.getAlbums();
    }

    public static Photo getPhotoFromAlbum(int albumIndex, int photoIndex)
    {
        if (user.getAlbum(albumIndex) == null)
            return null;
        return user.getAlbum(albumIndex).getPhoto(photoIndex);
    }

    public static boolean addAlbum(String albumName)
    {
        if (albumName == null)
            return false;
        if (user.addAlbum(new Album(albumName)))
        {
            saveUser();
            return true;
        }
        else
            return false;
    }

    public static boolean addPhotoToAlbum(int index, Bitmap bitmap, String filename)
    {
        Photo photoToAdd = new Photo(bitmap, filename);
        Album dest = user.getAlbum(index);

        // If photo does not yet exist in the destination album
        if (dest.getPhoto(photoToAdd) == null)
        {
            boolean isInPhotoBank = user.isInPhotoBank(photoToAdd);

            // If photo already exists in some other album
            if (isInPhotoBank)
            {
                // Do not add it (no dupes allowed between albums)
                return false;
            }
            // Get photoBank instance of the photo, and add it to the album
            else
            {
                user.getPhotoBank().add(photoToAdd);
                dest.addPhoto(photoToAdd);
                saveUser();

                return true;
            }
        }
        else
            return false;
    }

    public static String getLocationValue(int albumIndex, int photoIndex)
    {
        if (user.getAlbum(albumIndex) == null)
            return null;
        if (user.getAlbum(albumIndex).getPhoto(photoIndex) == null)
            return null;
        ArrayList<Tag> tags = user.getAlbum(albumIndex).getPhoto(photoIndex).getTags();

        for (Tag t : tags)
        {
            if (t.getKey().equals("LOCATION"))
                return t.getValue();
        }

        return null;
    }

    public static ArrayList<Tag> getPeopleTagOnly(int albumIndex, int photoIndex)
    {
        if (user.getAlbum(albumIndex) == null)
            return null;
        if (user.getAlbum(albumIndex).getPhoto(photoIndex) == null)
            return null;
        ArrayList<Tag> values = user.getAlbum(albumIndex).getPhoto(photoIndex).getTags();
        values.removeIf(p -> p.getKey().equals("LOCATION"));

        return values;
    }

    public static boolean deleteTag(int albumIndex, int photoIndex, String key, String value)
    {
        if (user.getAlbum(albumIndex) == null)
            return false;
        if (user.getAlbum(albumIndex).getPhoto(photoIndex) == null)
            return false;
        if (user.getAlbum(albumIndex).getPhoto(photoIndex).deleteTag(key, value))
        {
            saveUser();
            return true;
        }
        return false;
    }

    public static boolean addTagToPhoto(int albumIndex, int photoIndex, String key, String value)
    {
        if (user.getAlbum(albumIndex) == null)
            return false;
        if (user.getAlbum(albumIndex).getPhoto(photoIndex) == null)
            return false;
        if (value == null || value.isEmpty())
            return false;
        if (user.getAlbum(albumIndex).getPhoto(photoIndex).addTag(key, value))
        {
            saveUser();
            return true;
        }
        return false;
    }

    public static ArrayList<Tag> getAllTags()
    {
        ArrayList<Tag> tags = new ArrayList<Tag>();

        for (Album a : user.getAlbums())
        {
            for (Photo p : a.getPhotos())
            {
                if (!p.getTags().isEmpty())
                    tags.addAll(p.getTags());
            }
        }

        return tags;
    }

    public static void singleTagSearch(String key, String value)
    {
        ArrayList<Photo> results = new ArrayList<Photo>();

        for (Album a : user.getAlbums())
        {
            for (Photo p : a.getPhotos())
            {
                if (p.getTags().contains(new Tag(key, value)))
                    results.add(p);
            }
        }

        searchResults = results;
    }

    public static ArrayList<Photo> getSearchResults()
    {
        return searchResults;
    }

    public static void doubleTagSearch(Search type, String key1, String value1, String key2, String value2)
    {
        ArrayList<Photo> results = new ArrayList<Photo>();

        if (type == Search.OR)
        {
            for (Album a : user.getAlbums()) {
               for (Photo p : a.getPhotos()) {
                    boolean add = false;
                    for (Tag t : p.getTags())
                    {
                        String tKey = t.getKey();
                        String tVal = t.getValue();

                        if(tKey.equals(key1) && tVal.equals(value1))
                        {
                            add = true;
                            break;
                        }
                        else if (tKey.equals(key2) && tVal.equals(value2));
                        {
                            add = true;
                            break;
                        }
                    }
                    if (add)
                        results.add(p);
                }
            }
        }
        else if (type == Search.AND)
        {
            for (Album a : user.getAlbums())
            {
                for (Photo p : a.getPhotos())
                {
                    boolean match1 = false, match2 = false;
                    for (Tag t : p.getTags())
                    {
                        String tKey = t.getKey();
                        String tVal = t.getValue();

                        if(tKey.equals(key1) && tVal.equals(value1))
                        {
                            match1 = true;
                        }
                        else if (tKey.equals(key2) && tVal.equals(value2));
                        {
                            match2 = true;
                        }
                    }
                    if (match1 && match2)
                        results.add(p);
                }
            }
        }

        searchResults = results;
    }

}
