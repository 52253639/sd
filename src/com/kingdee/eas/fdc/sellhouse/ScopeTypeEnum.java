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
public class ScopeTypeEnum extends StringEnum
{
    public static final String ALLSCOPE_VALUE = "AllScope";
    public static final String ENACTMENTSCOPE_VALUE = "EnactmentScope";

    public static final ScopeTypeEnum AllScope = new ScopeTypeEnum("AllScope", ALLSCOPE_VALUE);
    public static final ScopeTypeEnum EnactmentScope = new ScopeTypeEnum("EnactmentScope", ENACTMENTSCOPE_VALUE);

    /**
     * construct function
     * @param String scopeTypeEnum
     */
    private ScopeTypeEnum(String name, String scopeTypeEnum)
    {
        super(name, scopeTypeEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static ScopeTypeEnum getEnum(String scopeTypeEnum)
    {
        return (ScopeTypeEnum)getEnum(ScopeTypeEnum.class, scopeTypeEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(ScopeTypeEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(ScopeTypeEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(ScopeTypeEnum.class);
    }
}