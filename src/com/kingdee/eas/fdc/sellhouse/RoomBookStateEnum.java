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
public class RoomBookStateEnum extends StringEnum
{
    public static final String NOTBOOKED_VALUE = "1NOTBOOKED";
    public static final String BOOKED_VALUE = "2BOOKED";
    public static final String BOOKING_VALUE = "3BOOKING";

    public static final RoomBookStateEnum NOTBOOKED = new RoomBookStateEnum("NOTBOOKED", NOTBOOKED_VALUE);
    public static final RoomBookStateEnum BOOKED = new RoomBookStateEnum("BOOKED", BOOKED_VALUE);
    public static final RoomBookStateEnum BOOKING = new RoomBookStateEnum("BOOKING", BOOKING_VALUE);

    /**
     * construct function
     * @param String roomBookStateEnum
     */
    private RoomBookStateEnum(String name, String roomBookStateEnum)
    {
        super(name, roomBookStateEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static RoomBookStateEnum getEnum(String roomBookStateEnum)
    {
        return (RoomBookStateEnum)getEnum(RoomBookStateEnum.class, roomBookStateEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(RoomBookStateEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(RoomBookStateEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(RoomBookStateEnum.class);
    }
}