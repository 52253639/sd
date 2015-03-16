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
public class RoomNumPrefixEnum extends StringEnum
{
    public static final String NOBUILDPREFIX_VALUE = "1noBuildPrefix";
    public static final String BUILDNUMPREFIX_VALUE = "2buildNumPrefix";
    public static final String BUILDNAMEPREFIX_VALUE = "3buildNamePrefix";

    public static final RoomNumPrefixEnum noBuildPrefix = new RoomNumPrefixEnum("noBuildPrefix", NOBUILDPREFIX_VALUE);
    public static final RoomNumPrefixEnum buildNumPrefix = new RoomNumPrefixEnum("buildNumPrefix", BUILDNUMPREFIX_VALUE);
    public static final RoomNumPrefixEnum buildNamePrefix = new RoomNumPrefixEnum("buildNamePrefix", BUILDNAMEPREFIX_VALUE);

    /**
     * construct function
     * @param String roomNumPrefixEnum
     */
    private RoomNumPrefixEnum(String name, String roomNumPrefixEnum)
    {
        super(name, roomNumPrefixEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static RoomNumPrefixEnum getEnum(String roomNumPrefixEnum)
    {
        return (RoomNumPrefixEnum)getEnum(RoomNumPrefixEnum.class, roomNumPrefixEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(RoomNumPrefixEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(RoomNumPrefixEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(RoomNumPrefixEnum.class);
    }
}