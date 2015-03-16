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
public class RoomActChangeStateEnum extends StringEnum
{
    public static final String NOCHANGE_VALUE = "noChange";
    public static final String CHANGE_VALUE = "change";

    public static final RoomActChangeStateEnum NOCHANGE = new RoomActChangeStateEnum("NOCHANGE", NOCHANGE_VALUE);
    public static final RoomActChangeStateEnum CHANGE = new RoomActChangeStateEnum("CHANGE", CHANGE_VALUE);

    /**
     * construct function
     * @param String roomActChangeStateEnum
     */
    private RoomActChangeStateEnum(String name, String roomActChangeStateEnum)
    {
        super(name, roomActChangeStateEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static RoomActChangeStateEnum getEnum(String roomActChangeStateEnum)
    {
        return (RoomActChangeStateEnum)getEnum(RoomActChangeStateEnum.class, roomActChangeStateEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(RoomActChangeStateEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(RoomActChangeStateEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(RoomActChangeStateEnum.class);
    }
}