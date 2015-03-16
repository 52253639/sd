/**
 * output package name
 */
package com.kingdee.eas.fdc.tenancy;

import java.util.Map;
import java.util.List;
import java.util.Iterator;
import com.kingdee.util.enums.StringEnum;

/**
 * output class name
 */
public class AttachSourceTypeEnum extends StringEnum
{
    public static final String NATATORIUM_VALUE = "1NATATORIUM";
    public static final String ADVERTISING_VALUE = "2ADVERTISING";
    public static final String PARK_VALUE = "3PARK";
    public static final String OTHER_VALUE = "4OTHER";

    public static final AttachSourceTypeEnum NATATORIUM = new AttachSourceTypeEnum("NATATORIUM", NATATORIUM_VALUE);
    public static final AttachSourceTypeEnum ADVERTISING = new AttachSourceTypeEnum("ADVERTISING", ADVERTISING_VALUE);
    public static final AttachSourceTypeEnum PARK = new AttachSourceTypeEnum("PARK", PARK_VALUE);
    public static final AttachSourceTypeEnum OTHER = new AttachSourceTypeEnum("OTHER", OTHER_VALUE);

    /**
     * construct function
     * @param String attachSourceTypeEnum
     */
    private AttachSourceTypeEnum(String name, String attachSourceTypeEnum)
    {
        super(name, attachSourceTypeEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static AttachSourceTypeEnum getEnum(String attachSourceTypeEnum)
    {
        return (AttachSourceTypeEnum)getEnum(AttachSourceTypeEnum.class, attachSourceTypeEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(AttachSourceTypeEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(AttachSourceTypeEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(AttachSourceTypeEnum.class);
    }
}