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
public class OccurreStateEnum extends StringEnum
{
    public static final String PRIOR_VALUE = "10";
    public static final String AFTER_VALUE = "20";

    public static final OccurreStateEnum PRIOR = new OccurreStateEnum("PRIOR", PRIOR_VALUE);
    public static final OccurreStateEnum AFTER = new OccurreStateEnum("AFTER", AFTER_VALUE);

    /**
     * construct function
     * @param String occurreStateEnum
     */
    private OccurreStateEnum(String name, String occurreStateEnum)
    {
        super(name, occurreStateEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static OccurreStateEnum getEnum(String occurreStateEnum)
    {
        return (OccurreStateEnum)getEnum(OccurreStateEnum.class, occurreStateEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(OccurreStateEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(OccurreStateEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(OccurreStateEnum.class);
    }
}