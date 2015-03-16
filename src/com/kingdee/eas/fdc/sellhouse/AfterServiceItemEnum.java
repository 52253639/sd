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
public class AfterServiceItemEnum extends StringEnum
{
    public static final String PROPERTY_VALUE = "PROPERTY";
    public static final String LOAN_VALUE = "LOAN";
    public static final String ACCUMULATION_VALUE = "ACCUMULATION";
    public static final String JOIN_VALUE = "JOIN";
    public static final String AREACOMPENSATE_VALUE = "AREACOMPENSATE";

    public static final AfterServiceItemEnum PROPERTY = new AfterServiceItemEnum("PROPERTY", PROPERTY_VALUE);
    public static final AfterServiceItemEnum LOAN = new AfterServiceItemEnum("LOAN", LOAN_VALUE);
    public static final AfterServiceItemEnum ACCUMULATION = new AfterServiceItemEnum("ACCUMULATION", ACCUMULATION_VALUE);
    public static final AfterServiceItemEnum JOIN = new AfterServiceItemEnum("JOIN", JOIN_VALUE);
    public static final AfterServiceItemEnum AREACOMPENSATE = new AfterServiceItemEnum("AREACOMPENSATE", AREACOMPENSATE_VALUE);

    /**
     * construct function
     * @param String afterServiceItemEnum
     */
    private AfterServiceItemEnum(String name, String afterServiceItemEnum)
    {
        super(name, afterServiceItemEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static AfterServiceItemEnum getEnum(String afterServiceItemEnum)
    {
        return (AfterServiceItemEnum)getEnum(AfterServiceItemEnum.class, afterServiceItemEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(AfterServiceItemEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(AfterServiceItemEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(AfterServiceItemEnum.class);
    }
}