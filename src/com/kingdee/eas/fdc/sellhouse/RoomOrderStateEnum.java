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
public class RoomOrderStateEnum extends StringEnum
{
    public static final String NOEXECUTE_VALUE = "noExecute";
    public static final String EXECUTED_VALUE = "executed";
    public static final String QUITORDER_VALUE = "quitOrder";

    public static final RoomOrderStateEnum noExecute = new RoomOrderStateEnum("noExecute", NOEXECUTE_VALUE);
    public static final RoomOrderStateEnum executed = new RoomOrderStateEnum("executed", EXECUTED_VALUE);
    public static final RoomOrderStateEnum quitOrder = new RoomOrderStateEnum("quitOrder", QUITORDER_VALUE);

    /**
     * construct function
     * @param String roomOrderStateEnum
     */
    private RoomOrderStateEnum(String name, String roomOrderStateEnum)
    {
        super(name, roomOrderStateEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static RoomOrderStateEnum getEnum(String roomOrderStateEnum)
    {
        return (RoomOrderStateEnum)getEnum(RoomOrderStateEnum.class, roomOrderStateEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(RoomOrderStateEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(RoomOrderStateEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(RoomOrderStateEnum.class);
    }
}