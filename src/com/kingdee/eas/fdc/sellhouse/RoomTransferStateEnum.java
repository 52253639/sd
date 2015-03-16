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
public class RoomTransferStateEnum extends StringEnum
{
    public static final String HASTRANSFER_VALUE = "HASTRANSFER";
    public static final String NOTTRANSFER_VALUE = "NOTTRANSFER";

    public static final RoomTransferStateEnum HASTRANSFER = new RoomTransferStateEnum("HASTRANSFER", HASTRANSFER_VALUE);
    public static final RoomTransferStateEnum NOTTRANSFER = new RoomTransferStateEnum("NOTTRANSFER", NOTTRANSFER_VALUE);

    /**
     * construct function
     * @param String roomTransferStateEnum
     */
    private RoomTransferStateEnum(String name, String roomTransferStateEnum)
    {
        super(name, roomTransferStateEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static RoomTransferStateEnum getEnum(String roomTransferStateEnum)
    {
        return (RoomTransferStateEnum)getEnum(RoomTransferStateEnum.class, roomTransferStateEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(RoomTransferStateEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(RoomTransferStateEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(RoomTransferStateEnum.class);
    }
}