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
public class ChangeBalanceObjectEnum extends StringEnum
{
    public static final String OLDROOMBALANCE_VALUE = "OldRoomBalance";
    public static final String NEWROOMBALANCE_VALUE = "NewRoomBalance";

    public static final ChangeBalanceObjectEnum OldRoomBalance = new ChangeBalanceObjectEnum("OldRoomBalance", OLDROOMBALANCE_VALUE);
    public static final ChangeBalanceObjectEnum NewRoomBalance = new ChangeBalanceObjectEnum("NewRoomBalance", NEWROOMBALANCE_VALUE);

    /**
     * construct function
     * @param String changeBalanceObjectEnum
     */
    private ChangeBalanceObjectEnum(String name, String changeBalanceObjectEnum)
    {
        super(name, changeBalanceObjectEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static ChangeBalanceObjectEnum getEnum(String changeBalanceObjectEnum)
    {
        return (ChangeBalanceObjectEnum)getEnum(ChangeBalanceObjectEnum.class, changeBalanceObjectEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(ChangeBalanceObjectEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(ChangeBalanceObjectEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(ChangeBalanceObjectEnum.class);
    }
}