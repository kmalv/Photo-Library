package softmeth.android.models;

import android.content.Context;

import java.io.FileInputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class Loader {
    private static final String filename = "info.data";

    private static ArrayList<Album> albums;

    public static ArrayList<Album> loadAllAlbums(Context context)
    {
        ArrayList<Album> albums = new ArrayList<Album>();
        try
        {
            FileInputStream in = context.openFileInput(filename);
            ObjectInputStream objIn = new ObjectInputStream(in);
            albums = (ArrayList<Album>)

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
