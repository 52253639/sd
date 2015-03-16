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
public class OverdueCauseTypeEnum extends StringEnum
{
    public static final String YSK_VALUE = "YSK";
    public static final String DD_VALUE = "DD";

    public static final OverdueCauseTypeEnum YSK = new OverdueCauseTypeEnum("YSK", YSK_VALUE);
    public static final OverdueCauseTypeEnum DD = new OverdueCauseTypeEnum("DD", DD_VALUE);

    /**
     * construct function
     * @param String overdueCauseTypeEnum
     */
    private OverdueCauseTypeEnum(String name, String overdueCauseTypeEnum)
    {
        super(name, overdueCauseTypeEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static OverdueCauseTypeEnum getEnum(String overdueCauseTypeEnum)
    {
        return (OverdueCauseTypeEnum)getEnum(OverdueCauseTypeEnum.class, overdueCauseTypeEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(OverdueCauseTypeEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(OverdueCauseTypeEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(OverdueCauseTypeEnum.class);
    }
}