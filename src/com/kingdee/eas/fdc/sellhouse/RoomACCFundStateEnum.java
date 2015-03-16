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
public class RoomACCFundStateEnum extends StringEnum
{
    public static final String NOTFUND_VALUE = "1NOTFUND";
    public static final String FUND_VALUE = "2FUND";

    public static final RoomACCFundStateEnum NOTFUND = new RoomACCFundStateEnum("NOTFUND", NOTFUND_VALUE);
    public static final RoomACCFundStateEnum FUND = new RoomACCFundStateEnum("FUND", FUND_VALUE);

    /**
     * construct function
     * @param String roomACCFundStateEnum
     */
    private RoomACCFundStateEnum(String name, String roomACCFundStateEnum)
    {
        super(name, roomACCFundStateEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static RoomACCFundStateEnum getEnum(String roomACCFundStateEnum)
    {
        return (RoomACCFundStateEnum)getEnum(RoomACCFundStateEnum.class, roomACCFundStateEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(RoomACCFundStateEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(RoomACCFundStateEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(RoomACCFundStateEnum.class);
    }
}