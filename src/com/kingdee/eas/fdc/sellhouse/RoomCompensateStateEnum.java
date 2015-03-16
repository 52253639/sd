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
public class RoomCompensateStateEnum extends StringEnum
{
    public static final String NOCOMPENSATE_VALUE = "NOCOMPENSATE";
    public static final String COMSUBMIT_VALUE = "COMSUBMIT";
    public static final String COMAUDITTING_VALUE = "COMAUDITTING";
    public static final String COMAUDITTED_VALUE = "COMAUDITTED";
    public static final String COMRECEIVED_VALUE = "COMRECEIVED";
    public static final String COMSTOP_VALUE = "COMSTOP";
    public static final String COMRECEIVING_VALUE = "COMRECEIVING";

    public static final RoomCompensateStateEnum NOCOMPENSATE = new RoomCompensateStateEnum("NOCOMPENSATE", NOCOMPENSATE_VALUE);
    public static final RoomCompensateStateEnum COMSUBMIT = new RoomCompensateStateEnum("COMSUBMIT", COMSUBMIT_VALUE);
    public static final RoomCompensateStateEnum COMAUDITTING = new RoomCompensateStateEnum("COMAUDITTING", COMAUDITTING_VALUE);
    public static final RoomCompensateStateEnum COMAUDITTED = new RoomCompensateStateEnum("COMAUDITTED", COMAUDITTED_VALUE);
    public static final RoomCompensateStateEnum COMRECEIVED = new RoomCompensateStateEnum("COMRECEIVED", COMRECEIVED_VALUE);
    public static final RoomCompensateStateEnum COMSTOP = new RoomCompensateStateEnum("COMSTOP", COMSTOP_VALUE);
    public static final RoomCompensateStateEnum COMRECEIVING = new RoomCompensateStateEnum("COMRECEIVING", COMRECEIVING_VALUE);

    /**
     * construct function
     * @param String roomCompensateStateEnum
     */
    private RoomCompensateStateEnum(String name, String roomCompensateStateEnum)
    {
        super(name, roomCompensateStateEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static RoomCompensateStateEnum getEnum(String roomCompensateStateEnum)
    {
        return (RoomCompensateStateEnum)getEnum(RoomCompensateStateEnum.class, roomCompensateStateEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(RoomCompensateStateEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(RoomCompensateStateEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(RoomCompensateStateEnum.class);
    }
}