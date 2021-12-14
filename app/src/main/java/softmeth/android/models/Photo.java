package softmeth.android.models;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Represents a Photo. Each Photo has a name, filename, and a list
 * of Tags. 
 * Contains mostly protected methods
 * to prevent accidental data manipulation.
 * @author <em>kma224</em> - Kayla Alvarado
 * @author <em>ey150</em>  - Ernest Yakobchuk
 */
public class Photo implements Serializable {
    private Bitmap         image;
    private String         filename;
    private ArrayList<Tag> tags;

    /**
     * Constructor of a Photo instance given only the path of
     * the photo. Caption and tags default to empty
     */
    public Photo(Bitmap image, String filename)
    {
        this.image     = image;
        this.filename  = filename;
        this.tags      = new ArrayList<Tag>();
    }
    
    /**
     * Gets the Bitmap associated with the photo
     * @return Bitmap of the image
     */
    public Bitmap getImage()
    {
        return this.image;
    }

    /** 
     * Sets the filename of this photo
     * @param filename desired filename for the photo
     */
    public void setFilename(String filename)
    {
        this.filename = filename;
    }

    /** 
     * Gets the filename of this photo
    * @return the filename of this photo as a String
     */
    public String getFilename()
    {
        return this.filename;
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

    protected boolean addTag(String key, String value)
    {
        if (this.tags.contains(new Tag(key, value)))
            return false;
        return this.tags.add(new Tag(key, value));
    }

    protected boolean deleteTag(String key, String value)
    {
        if (!this.tags.contains(new Tag(key, value)))
            return false;
        return this.tags.remove(new Tag(key, value));
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
            if (p.filename.equals(this.filename))
                return true;
        }
        return false;
    }


    private void writeObject(java.io.ObjectOutputStream out)
    {
        try
        {
            // Compress the bitmap before saving it
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            this.image.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] byteArray = stream.toByteArray();

            out.writeInt(byteArray.length);
            out.write(byteArray);

            out.writeObject(this.filename);
            out.writeObject(this.tags);
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
            // Read the Bitmap bytearray
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

            // Read/save the rest of the fields
            filename = (String) in.readObject();
            tags = (ArrayList<Tag>) in.readObject();
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
