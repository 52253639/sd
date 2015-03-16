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
public class RoomWindowsEnum extends StringEnum
{
    public static final String YES_VALUE = "YES";
    public static final String NOT_VALUE = "NOT";

    public static final RoomWindowsEnum yes = new RoomWindowsEnum("yes", YES_VALUE);
    public static final RoomWindowsEnum not = new RoomWindowsEnum("not", NOT_VALUE);

    /**
     * construct function
     * @param String roomWindowsEnum
     */
    private RoomWindowsEnum(String name, String roomWindowsEnum)
    {
        super(name, roomWindowsEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static RoomWindowsEnum getEnum(String roomWindowsEnum)
    {
        return (RoomWindowsEnum)getEnum(RoomWindowsEnum.class, roomWindowsEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(RoomWindowsEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(RoomWindowsEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(RoomWindowsEnum.class);
    }
}