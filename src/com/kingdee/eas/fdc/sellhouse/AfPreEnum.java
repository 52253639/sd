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
public class AfPreEnum extends StringEnum
{
    public static final String TENTHOUSAND_VALUE = "tenthousand";
    public static final String THOUSAND_VALUE = "thousand";
    public static final String HUNDRED_VALUE = "hundred";
    public static final String TEN_VALUE = "ten";

    public static final AfPreEnum TENTHOUSAND = new AfPreEnum("TENTHOUSAND", TENTHOUSAND_VALUE);
    public static final AfPreEnum THOUSAND = new AfPreEnum("THOUSAND", THOUSAND_VALUE);
    public static final AfPreEnum HUNDRED = new AfPreEnum("HUNDRED", HUNDRED_VALUE);
    public static final AfPreEnum TEN = new AfPreEnum("TEN", TEN_VALUE);

    /**
     * construct function
     * @param String afPreEnum
     */
    private AfPreEnum(String name, String afPreEnum)
    {
        super(name, afPreEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static AfPreEnum getEnum(String afPreEnum)
    {
        return (AfPreEnum)getEnum(AfPreEnum.class, afPreEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(AfPreEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(AfPreEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(AfPreEnum.class);
    }
}