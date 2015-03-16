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
public class DealStateEnum extends StringEnum
{
    public static final String DEAL_VALUE = "deal";
    public static final String NOTDEAL_VALUE = "notDeal";

    public static final DealStateEnum DEAL = new DealStateEnum("DEAL", DEAL_VALUE);
    public static final DealStateEnum NOTDEAL = new DealStateEnum("NOTDEAL", NOTDEAL_VALUE);

    /**
     * construct function
     * @param String dealStateEnum
     */
    private DealStateEnum(String name, String dealStateEnum)
    {
        super(name, dealStateEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static DealStateEnum getEnum(String dealStateEnum)
    {
        return (DealStateEnum)getEnum(DealStateEnum.class, dealStateEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(DealStateEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(DealStateEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(DealStateEnum.class);
    }
}