/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.supplier;

import java.util.Map;
import java.util.List;
import java.util.Iterator;
import com.kingdee.util.enums.StringEnum;

/**
 * output class name
 */
public class AppraiseTypeEnum extends StringEnum
{
    public static final String WEIGHT_VALUE = "WEIGHT";
    public static final String PASS_VALUE = "PASS";

    public static final AppraiseTypeEnum WEIGHT = new AppraiseTypeEnum("WEIGHT", WEIGHT_VALUE);
    public static final AppraiseTypeEnum PASS = new AppraiseTypeEnum("PASS", PASS_VALUE);

    /**
     * construct function
     * @param String appraiseTypeEnum
     */
    private AppraiseTypeEnum(String name, String appraiseTypeEnum)
    {
        super(name, appraiseTypeEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static AppraiseTypeEnum getEnum(String appraiseTypeEnum)
    {
        return (AppraiseTypeEnum)getEnum(AppraiseTypeEnum.class, appraiseTypeEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(AppraiseTypeEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(AppraiseTypeEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(AppraiseTypeEnum.class);
    }
}