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
public class BaseDataPropertyEnum extends StringEnum
{
    public static final String CUCONTROL_VALUE = "CUControl";
    public static final String PROJECTCONTROL_VALUE = "ProjectControl";

    public static final BaseDataPropertyEnum CUControl = new BaseDataPropertyEnum("CUControl", CUCONTROL_VALUE);
    public static final BaseDataPropertyEnum ProjectControl = new BaseDataPropertyEnum("ProjectControl", PROJECTCONTROL_VALUE);

    /**
     * construct function
     * @param String baseDataPropertyEnum
     */
    private BaseDataPropertyEnum(String name, String baseDataPropertyEnum)
    {
        super(name, baseDataPropertyEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static BaseDataPropertyEnum getEnum(String baseDataPropertyEnum)
    {
        return (BaseDataPropertyEnum)getEnum(BaseDataPropertyEnum.class, baseDataPropertyEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(BaseDataPropertyEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(BaseDataPropertyEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(BaseDataPropertyEnum.class);
    }
}