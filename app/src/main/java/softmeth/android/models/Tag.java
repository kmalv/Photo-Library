/**
 * @author Kayla Alvarado kma224
 * @author Ernest Yakobchuk ey150
 */
package softmeth.android.models;

import java.io.Serializable;

/**
 * @author <em>kma224</em> - Kayla Alvarado
 * @author <em>ey150</em>  - Ernest Yakobchuk
 */
public class Tag implements Serializable {
    private String  key;
    private String  value;

    public Tag(String key, String value)
    {
        this.key               = key.toUpperCase();
        this.value             = value.toLowerCase();
    }

    /** 
     * @param o
     * @return boolean
     */
    @Override
    public boolean equals(Object o)
    {
        if (o instanceof Tag)
        {
            Tag t = (Tag) o;
            if (this.key.equals("LOCATION"))
                return t.getKey().equals("LOCATION");
            else if (this.key.equals("PERSON"))
                return t.getKey().equals("PERSON") && this.value.equals(t.getValue());

            /*            boolean sameKey = this.key.equals(t.key);
            boolean canDupe = this.duplicatesAllowed && t.duplicatesAllowed;
            if (sameKey && canDupe)
                return this.value.equals(t.value);
            if (sameKey && !canDupe)
                return true;*/
        }
        return false;
    }

    /** 
     * @return String
     */
    @Override
    public String toString()
    {
        return this.getKey() + ": " + this.getValue();
    }

    /** 
     * @return String
     */
    protected String getKey()
    {
        return this.key;
    }
    
    /** 
     * @return String
     */
    public String getValue()
    {
        return this.value;
    }

    protected boolean setValue(String value)
    {
        if (this.getKey().equals("LOCATION"))
        {
            this.value = value;
            return true;
        }
        else
            return false;
    }
}
