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
public class ConditionTypeEnum extends StringEnum
{
    public static final String CENTER_VALUE = "1CENTER";
    public static final String FISSION_VALUE = "2FISSION";
    public static final String OTHER_VALUE = "2OTHER";

    public static final ConditionTypeEnum CENTER = new ConditionTypeEnum("CENTER", CENTER_VALUE);
    public static final ConditionTypeEnum FISSION = new ConditionTypeEnum("FISSION", FISSION_VALUE);
    public static final ConditionTypeEnum OTHER = new ConditionTypeEnum("OTHER", OTHER_VALUE);

    /**
     * construct function
     * @param String conditionTypeEnum
     */
    private ConditionTypeEnum(String name, String conditionTypeEnum)
    {
        super(name, conditionTypeEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static ConditionTypeEnum getEnum(String conditionTypeEnum)
    {
        return (ConditionTypeEnum)getEnum(ConditionTypeEnum.class, conditionTypeEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(ConditionTypeEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(ConditionTypeEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(ConditionTypeEnum.class);
    }
}