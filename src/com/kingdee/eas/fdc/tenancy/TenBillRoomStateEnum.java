/**
 * output package name
 */
package com.kingdee.eas.fdc.tenancy;

import java.util.Map;
import java.util.List;
import java.util.Iterator;
import com.kingdee.util.enums.StringEnum;

/**
 * output class name
 */
public class TenBillRoomStateEnum extends StringEnum
{
    public static final String SELL_VALUE = "SELL";//alias=已售
    public static final String TEN_VALUE = "TEN";//alias=出租用

    public static final TenBillRoomStateEnum SELL = new TenBillRoomStateEnum("SELL", SELL_VALUE);
    public static final TenBillRoomStateEnum TEN = new TenBillRoomStateEnum("TEN", TEN_VALUE);

    /**
     * construct function
     * @param String tenBillRoomStateEnum
     */
    private TenBillRoomStateEnum(String name, String tenBillRoomStateEnum)
    {
        super(name, tenBillRoomStateEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static TenBillRoomStateEnum getEnum(String tenBillRoomStateEnum)
    {
        return (TenBillRoomStateEnum)getEnum(TenBillRoomStateEnum.class, tenBillRoomStateEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(TenBillRoomStateEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(TenBillRoomStateEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(TenBillRoomStateEnum.class);
    }
}