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
public class OperateEnum extends StringEnum
{
    public static final String MULTIPLY_VALUE = "MULTIPLY";
    public static final String ADD_VALUE = "ADD";
    public static final String MINUS_VALUE = "MINUS";
    public static final String DIVIDE_VALUE = "DIVIDE";

    public static final OperateEnum MULTIPLY = new OperateEnum("MULTIPLY", MULTIPLY_VALUE);
    public static final OperateEnum ADD = new OperateEnum("ADD", ADD_VALUE);
    public static final OperateEnum MINUS = new OperateEnum("MINUS", MINUS_VALUE);
    public static final OperateEnum DIVIDE = new OperateEnum("DIVIDE", DIVIDE_VALUE);

    /**
     * construct function
     * @param String operateEnum
     */
    private OperateEnum(String name, String operateEnum)
    {
        super(name, operateEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static OperateEnum getEnum(String operateEnum)
    {
        return (OperateEnum)getEnum(OperateEnum.class, operateEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(OperateEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(OperateEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(OperateEnum.class);
    }
}