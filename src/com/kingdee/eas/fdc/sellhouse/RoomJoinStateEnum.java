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
public class RoomJoinStateEnum extends StringEnum
{
    public static final String NOTJOIN_VALUE = "1NOTJOIN";
    public static final String JOINED_VALUE = "2JOINED";
    public static final String JOINING_VALUE = "3JOINING";

    public static final RoomJoinStateEnum NOTJOIN = new RoomJoinStateEnum("NOTJOIN", NOTJOIN_VALUE);
    public static final RoomJoinStateEnum JOINED = new RoomJoinStateEnum("JOINED", JOINED_VALUE);
    public static final RoomJoinStateEnum JOINING = new RoomJoinStateEnum("JOINING", JOINING_VALUE);

    /**
     * construct function
     * @param String roomJoinStateEnum
     */
    private RoomJoinStateEnum(String name, String roomJoinStateEnum)
    {
        super(name, roomJoinStateEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static RoomJoinStateEnum getEnum(String roomJoinStateEnum)
    {
        return (RoomJoinStateEnum)getEnum(RoomJoinStateEnum.class, roomJoinStateEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(RoomJoinStateEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(RoomJoinStateEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(RoomJoinStateEnum.class);
    }
}