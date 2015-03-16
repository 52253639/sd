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
public class RoomListSeparatorEnum extends StringEnum
{
    public static final String SPACE_VALUE = "1SPACE";
    public static final String LITTLERAIL_VALUE = "2LittleRail";
    public static final String LEFTSEPARATOR_VALUE = "3LEFTSEPARATOR";
    public static final String RIGHTSEPARATOR_VALUE = "4RIGHTSEPARATOR";

    public static final RoomListSeparatorEnum Space = new RoomListSeparatorEnum("Space", SPACE_VALUE);
    public static final RoomListSeparatorEnum LittleRail = new RoomListSeparatorEnum("LittleRail", LITTLERAIL_VALUE);
    public static final RoomListSeparatorEnum LeftSeparator = new RoomListSeparatorEnum("LeftSeparator", LEFTSEPARATOR_VALUE);
    public static final RoomListSeparatorEnum RightSeparator = new RoomListSeparatorEnum("RightSeparator", RIGHTSEPARATOR_VALUE);

    /**
     * construct function
     * @param String roomListSeparatorEnum
     */
    private RoomListSeparatorEnum(String name, String roomListSeparatorEnum)
    {
        super(name, roomListSeparatorEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static RoomListSeparatorEnum getEnum(String roomListSeparatorEnum)
    {
        return (RoomListSeparatorEnum)getEnum(RoomListSeparatorEnum.class, roomListSeparatorEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(RoomListSeparatorEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(RoomListSeparatorEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(RoomListSeparatorEnum.class);
    }
}