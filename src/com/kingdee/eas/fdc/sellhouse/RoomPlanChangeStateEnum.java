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
public class RoomPlanChangeStateEnum extends StringEnum
{
    public static final String NOCHANCE_VALUE = "noChange";
    public static final String CHANGE_VALUE = "change";

    public static final RoomPlanChangeStateEnum NOCHANCE = new RoomPlanChangeStateEnum("NOCHANCE", NOCHANCE_VALUE);
    public static final RoomPlanChangeStateEnum CHANGE = new RoomPlanChangeStateEnum("CHANGE", CHANGE_VALUE);

    /**
     * construct function
     * @param String roomPlanChangeStateEnum
     */
    private RoomPlanChangeStateEnum(String name, String roomPlanChangeStateEnum)
    {
        super(name, roomPlanChangeStateEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static RoomPlanChangeStateEnum getEnum(String roomPlanChangeStateEnum)
    {
        return (RoomPlanChangeStateEnum)getEnum(RoomPlanChangeStateEnum.class, roomPlanChangeStateEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(RoomPlanChangeStateEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(RoomPlanChangeStateEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(RoomPlanChangeStateEnum.class);
    }
}