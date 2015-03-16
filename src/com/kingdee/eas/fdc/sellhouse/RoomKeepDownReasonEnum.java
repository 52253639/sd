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
public class RoomKeepDownReasonEnum extends StringEnum
{
    public static final String CONTROL_VALUE = "CONTROL";
    public static final String CUSTOMERKEEP_VALUE = "CUSTOMERKEEP";

    public static final RoomKeepDownReasonEnum CONTROl = new RoomKeepDownReasonEnum("CONTROl", CONTROL_VALUE);
    public static final RoomKeepDownReasonEnum CUSTOMERKEEP = new RoomKeepDownReasonEnum("CUSTOMERKEEP", CUSTOMERKEEP_VALUE);

    /**
     * construct function
     * @param String roomKeepDownReasonEnum
     */
    private RoomKeepDownReasonEnum(String name, String roomKeepDownReasonEnum)
    {
        super(name, roomKeepDownReasonEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static RoomKeepDownReasonEnum getEnum(String roomKeepDownReasonEnum)
    {
        return (RoomKeepDownReasonEnum)getEnum(RoomKeepDownReasonEnum.class, roomKeepDownReasonEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(RoomKeepDownReasonEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(RoomKeepDownReasonEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(RoomKeepDownReasonEnum.class);
    }
}