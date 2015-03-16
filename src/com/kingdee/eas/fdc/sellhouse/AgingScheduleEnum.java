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
public class AgingScheduleEnum extends StringEnum
{
    public static final String OVERDUEALL_VALUE = "0";
    public static final String OVERDUEARR_VALUE = "1";
    public static final String OVERDUEPAYOFF_VALUE = "2";

    public static final AgingScheduleEnum OVERDUEALL = new AgingScheduleEnum("OVERDUEALL", OVERDUEALL_VALUE);
    public static final AgingScheduleEnum OVERDUEARR = new AgingScheduleEnum("OVERDUEARR", OVERDUEARR_VALUE);
    public static final AgingScheduleEnum OVERDUEPAYOFF = new AgingScheduleEnum("OVERDUEPAYOFF", OVERDUEPAYOFF_VALUE);

    /**
     * construct function
     * @param String agingScheduleEnum
     */
    private AgingScheduleEnum(String name, String agingScheduleEnum)
    {
        super(name, agingScheduleEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static AgingScheduleEnum getEnum(String agingScheduleEnum)
    {
        return (AgingScheduleEnum)getEnum(AgingScheduleEnum.class, agingScheduleEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(AgingScheduleEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(AgingScheduleEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(AgingScheduleEnum.class);
    }
}