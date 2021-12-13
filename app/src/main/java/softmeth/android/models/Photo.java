package softmeth.android.models;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import java.io.ByteArrayOutputStream;
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
    private Bitmap         image;
    private String         caption;
    private ArrayList<Tag> tags;

    /**
     * Constructor of a Photo instance given only the path of
     * the photo. Caption and tags default to empty
     */
    public Photo(Bitmap image)
    {
        this.image     = image;
        this.caption  = null;
        this.tags     = new ArrayList<Tag>();
    }
    
    
    /** 
     * Gets the path of this photo
     * @return the path as a String
     */
//    public String getPath()
//    {
//        return this.uri.toString();
//    }

    /**
     * Gets the Bitmap associated with the photo
     * @return Bitmap of the image
     */
    public Bitmap getImage()
    {
        return this.image;
    }

    /** 
     * Sets the caption of this photo
     * @param caption desired caption for the photo
     */
    public void setCaption(String caption)
    {
        this.caption = caption;
    }

    /** 
     * Gets the caption of this photo
    * @return the caption of this photo as a String
     */
    public String getCaption()
    {
        return this.caption;
    }

    /** 
     * Gets the list of tags of this photo
     * @return ArrayList of {@link Tag} objects associated
     * with this photo
     */
    public ArrayList<Tag> getTags()
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
            if (p.image.equals(this.image))
                return true;
        }
        return false;
    }


    private void writeObject(java.io.ObjectOutputStream out)
    {
        try
        {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            image.compress(Bitmap.CompressFormat.PNG, 100, stream);

            byte[] byteArray = stream.toByteArray();

            out.writeInt(byteArray.length);
            out.write(byteArray);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    private void readObject(java.io.ObjectInputStream in)
    {
        try
        {
            int bufferLength = in.readInt();

            byte[] byteArray = new byte[bufferLength];

            int pos = 0;
            do {
                int read = in.read(byteArray, pos, bufferLength - pos);

                if (read != -1) {
                    pos += read;
                } else {
                    break;
                }

            } while (pos < bufferLength);

            image = BitmapFactory.decodeByteArray(byteArray, 0, bufferLength);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

/*    public FileInputStream getStream()
    {
        Uri uri = this.uri;
        try
        {
            FileInputStream is = new FileInputStream(uri.getPath());
            return is;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }*/
}
