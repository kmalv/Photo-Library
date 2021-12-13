package softmeth.android.models;

import android.content.Context;

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
    private static User user;

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

    public static void saveUser()
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
        boolean deleted = user.deleteAlbum(index);
        if (deleted)
            saveUser();
        return deleted;
    }

    public static boolean renameAlbum(int index, String newName)
    {
        if (user == null)
            return false;
        Album album = user.getAlbum(index);
        if (album != null)
        {
            album.setName(newName);
            saveUser();
            return true;
        }
        return false;
    }
}
