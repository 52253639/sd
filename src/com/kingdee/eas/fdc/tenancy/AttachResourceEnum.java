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
public class AttachResourceEnum extends StringEnum
{
    public static final String ATTACHRESOURCE_VALUE = "1ATTACHRESOURCE";
    public static final String NOTATTACHRESOURCE_VALUE = "2NOTATTACHRESOURCE";

    public static final AttachResourceEnum ATTACHRESOURCE = new AttachResourceEnum("ATTACHRESOURCE", ATTACHRESOURCE_VALUE);
    public static final AttachResourceEnum NOTATTACHRESOURCE = new AttachResourceEnum("NOTATTACHRESOURCE", NOTATTACHRESOURCE_VALUE);

    /**
     * construct function
     * @param String attachResourceEnum
     */
    private AttachResourceEnum(String name, String attachResourceEnum)
    {
        super(name, attachResourceEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static AttachResourceEnum getEnum(String attachResourceEnum)
    {
        return (AttachResourceEnum)getEnum(AttachResourceEnum.class, attachResourceEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(AttachResourceEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(AttachResourceEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(AttachResourceEnum.class);
    }
}