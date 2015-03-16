/**
 * output package name
 */
package com.kingdee.eas.fdc.market;

import java.util.Map;
import java.util.List;
import java.util.Iterator;
import com.kingdee.util.enums.StringEnum;

/**
 * output class name
 */
public class JumpConditionEnum extends StringEnum
{
    public static final String NULL_VALUE = "Null";
    public static final String CHOOSE_VALUE = "Choose";
    public static final String UNCHOOSE_VALUE = "UnChoose";

    public static final JumpConditionEnum Null = new JumpConditionEnum("Null", NULL_VALUE);
    public static final JumpConditionEnum Choose = new JumpConditionEnum("Choose", CHOOSE_VALUE);
    public static final JumpConditionEnum UnChoose = new JumpConditionEnum("UnChoose", UNCHOOSE_VALUE);

    /**
     * construct function
     * @param String jumpConditionEnum
     */
    private JumpConditionEnum(String name, String jumpConditionEnum)
    {
        super(name, jumpConditionEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static JumpConditionEnum getEnum(String jumpConditionEnum)
    {
        return (JumpConditionEnum)getEnum(JumpConditionEnum.class, jumpConditionEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(JumpConditionEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(JumpConditionEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(JumpConditionEnum.class);
    }
}