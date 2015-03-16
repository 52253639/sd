/**
 * output package name
 */
package com.kingdee.eas.fdc.basecrm;

import java.util.Map;
import java.util.List;
import java.util.Iterator;
import com.kingdee.util.enums.StringEnum;

/**
 * output class name
 */
public class ContactPreferencesEnum extends StringEnum
{
    public static final String ANY_VALUE = "ANY";
    public static final String PHONE_VALUE = "PHONE";
    public static final String TEXTMESSAGE_VALUE = "TEXTMESSAGE";
    public static final String EMAIL_VALUE = "EMAIL";
    public static final String LETTERS_VALUE = "LETTERS";

    public static final ContactPreferencesEnum ANY = new ContactPreferencesEnum("ANY", ANY_VALUE);
    public static final ContactPreferencesEnum PHONE = new ContactPreferencesEnum("PHONE", PHONE_VALUE);
    public static final ContactPreferencesEnum TEXTMESSAGE = new ContactPreferencesEnum("TEXTMESSAGE", TEXTMESSAGE_VALUE);
    public static final ContactPreferencesEnum EMAIL = new ContactPreferencesEnum("EMAIL", EMAIL_VALUE);
    public static final ContactPreferencesEnum LETTERS = new ContactPreferencesEnum("LETTERS", LETTERS_VALUE);

    /**
     * construct function
     * @param String contactPreferencesEnum
     */
    private ContactPreferencesEnum(String name, String contactPreferencesEnum)
    {
        super(name, contactPreferencesEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static ContactPreferencesEnum getEnum(String contactPreferencesEnum)
    {
        return (ContactPreferencesEnum)getEnum(ContactPreferencesEnum.class, contactPreferencesEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(ContactPreferencesEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(ContactPreferencesEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(ContactPreferencesEnum.class);
    }
}