/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse;

import java.util.Map;
import java.util.List;
import java.util.Iterator;
import com.kingdee.util.enums.StringEnum;

/**
 * output class name
 */
public class MortagageEnum extends StringEnum
{
    public static final String SAVED_VALUE = "1SAVED";
    public static final String SUBMITTED_VALUE = "2SUBMITTED";
    public static final String AUDITTING_VALUE = "3AUDITTING";
    public static final String AUDITTED_VALUE = "4AUDITTED";
    public static final String CANCEL_VALUE = "5CANCEL";
    public static final String MORTAGAGE_VALUE = "6MORTAGAGE";
    public static final String ANTIMORTAGAGE_VALUE = "7ANTIMORTAGAGE";

    public static final MortagageEnum SAVED = new MortagageEnum("SAVED", SAVED_VALUE);
    public static final MortagageEnum SUBMITTED = new MortagageEnum("SUBMITTED", SUBMITTED_VALUE);
    public static final MortagageEnum AUDITTING = new MortagageEnum("AUDITTING", AUDITTING_VALUE);
    public static final MortagageEnum AUDITTED = new MortagageEnum("AUDITTED", AUDITTED_VALUE);
    public static final MortagageEnum CANCEL = new MortagageEnum("CANCEL", CANCEL_VALUE);
    public static final MortagageEnum MORTAGAGE = new MortagageEnum("MORTAGAGE", MORTAGAGE_VALUE);
    public static final MortagageEnum ANTIMORTAGAGE = new MortagageEnum("ANTIMORTAGAGE", ANTIMORTAGAGE_VALUE);

    /**
     * construct function
     * @param String mortagageEnum
     */
    private MortagageEnum(String name, String mortagageEnum)
    {
        super(name, mortagageEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static MortagageEnum getEnum(String mortagageEnum)
    {
        return (MortagageEnum)getEnum(MortagageEnum.class, mortagageEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(MortagageEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(MortagageEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(MortagageEnum.class);
    }
}