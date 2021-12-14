/*
package softmeth.android.models;

import java.io.FileInputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Comparator;

public class SearchActions {
    public enum SearchType {
        TAG, DATE, AND, OR, SINGLE
    }
    private static List<Photo> results;

    
    */
/**
     * @param earliest
     * @param latest
     * @return List<Photo>
     *//*

    public static List<Photo> searchByDate(LocalDateTime earliest, LocalDateTime latest)
    {
        List<Photo> list = UserActions.getPhotoBank();

        list.removeIf(p ->
            {
                boolean inRange = p.getLastModifiedDateAsTime().isBefore(earliest); 
                inRange = inRange || p.getLastModifiedDateAsTime().isAfter(latest);
                return inRange;
            }
        );
        
        results = list;
        return list;
    }

    
    */
/**
     * @return List<Photo>
     *//*

    public static List<Photo> getResults()
    {
        return results;
    }

    
    */
/**
     * @return ArrayList<Photo>
     *//*

    private static ArrayList<Photo> getUserPhotoBank()
    {
        return UserActions.getPhotoBank();
    }

    
    */
/**
     * @param photo
     * @return String
     *//*

    public static String getFilename(Photo photo)
    {
        ArrayList<Photo> photoBank = getUserPhotoBank();
        if (photoBank == null)
            return "(user has no photos, this shouldnt happen)";
        
        int index = photoBank.indexOf(photo);
        
        if (index == -1)
            return "(photo not in photoBank)";
        return photoBank.get(index).getFilename();
    }

    
    */
/**
     * @param photo
     * @return FileInputStream
     *//*

    public static FileInputStream getStream(Photo photo)
    {
        ArrayList<Photo> photoBank = getUserPhotoBank();
        if (photoBank == null)
            return null;
        int index = photoBank.indexOf(photo);
        if (index == -1)
            return null;
        return photoBank.get(index).getStream();    
    }

    
    */
/**
     * @param name
     * @return boolean
     *//*

    public static boolean createAlbumFromResults(String name)
    {
        if (UserActions.addAlbum(name))
        {
            for (Photo p : results)
            {
                UserActions.addPhotoToAlbum(new Album(name), p);
            }
            return true;
        }
        return false;
    }

    
    */
/**
     * @param key1
     * @param value1
     * @return List<Photo>
     *//*

    public static List<Photo> searchByOneTag(String key1, String value1)
    {
        List<Photo> photoBank = UserActions.getPhotoBank();
        if (photoBank == null)
            return null;
        photoBank.removeIf(photo ->
            {
                for (Tag t : photo.getTags())
                {
                    boolean match1 = t.getKey().equals(key1) && t.getValue().equals(value1);

                    if (match1)
                        return false;
                }
                return true;
            }
        );

        results = photoBank;
        return photoBank;
    }

    
    */
/**
     * @param key1
     * @param key2
     * @param value1
     * @param value2
     * @return List<Photo>
     *//*

    public static List<Photo> searchByTwoTagsAnd(String key1, String key2, String value1, String value2)
    {
        List<Photo> photoBank = UserActions.getPhotoBank();
        if (photoBank == null)
            return null;
        photoBank.removeIf(photo ->
            {
                boolean hasTag1 = false;
                boolean hasTag2 = false;
                for (Tag t : photo.getTags())
                {
                    boolean match1 = t.getKey().equals(key1) && t.getValue().equals(value1);
                    boolean match2 = t.getKey().equals(key2) && t.getValue().equals(value2);

                    if (match1)
                        hasTag1 = true;
                    if (match2)
                        hasTag2 = true;
                }
                return !(hasTag1 && hasTag2);
            }
        );

        results = photoBank;
        return photoBank;
    }

    
    */
/**
     * @param key1
     * @param key2
     * @param value1
     * @param value2
     * @return List<Photo>
     *//*

    public static List<Photo> searchByTwoTagsOr(String key1, String key2, String value1, String value2)
    {
        List<Photo> photoBank = UserActions.getPhotoBank();
        if (photoBank == null)
            return null;
        photoBank.removeIf(photo ->
            {
                for (Tag t : photo.getTags())
                {
                    boolean match1 = t.getKey().equals(key1) && t.getValue().equals(value1);
                    boolean match2 = t.getKey().equals(key2) && t.getValue().equals(value2);

                    if (match1 || match2)
                        return false;
                }
                return true;
            }
        );

        results = photoBank;
        return photoBank;
    }
}
*/
