/**
 * output package name
 */
package com.kingdee.eas.fdc.schedule;

import java.util.Map;
import java.util.List;
import java.util.Iterator;
import com.kingdee.util.enums.StringEnum;

/**
 * output class name
 */
public class GuideTypeEnum extends StringEnum
{
    public static final String SYSTEMFLOW_VALUE = "SYSFLOW";
    public static final String REFERENCECASE_VALUE = "REFERENCECASE";

    public static final GuideTypeEnum systemFlow = new GuideTypeEnum("systemFlow", SYSTEMFLOW_VALUE);
    public static final GuideTypeEnum referenceCase = new GuideTypeEnum("referenceCase", REFERENCECASE_VALUE);

    /**
     * construct function
     * @param String guideTypeEnum
     */
    private GuideTypeEnum(String name, String guideTypeEnum)
    {
        super(name, guideTypeEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static GuideTypeEnum getEnum(String guideTypeEnum)
    {
        return (GuideTypeEnum)getEnum(GuideTypeEnum.class, guideTypeEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(GuideTypeEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(GuideTypeEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(GuideTypeEnum.class);
    }
}