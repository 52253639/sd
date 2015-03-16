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
public class HousePropertyEnum extends StringEnum
{
    public static final String ATTACHMENT_VALUE = "Attachment";
    public static final String NOATTACHMENT_VALUE = "NoAttachment";

    public static final HousePropertyEnum Attachment = new HousePropertyEnum("Attachment", ATTACHMENT_VALUE);
    public static final HousePropertyEnum NoAttachment = new HousePropertyEnum("NoAttachment", NOATTACHMENT_VALUE);

    /**
     * construct function
     * @param String housePropertyEnum
     */
    private HousePropertyEnum(String name, String housePropertyEnum)
    {
        super(name, housePropertyEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static HousePropertyEnum getEnum(String housePropertyEnum)
    {
        return (HousePropertyEnum)getEnum(HousePropertyEnum.class, housePropertyEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(HousePropertyEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(HousePropertyEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(HousePropertyEnum.class);
    }
}