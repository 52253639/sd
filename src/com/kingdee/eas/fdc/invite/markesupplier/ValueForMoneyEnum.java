/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.markesupplier;

import java.util.Map;
import java.util.List;
import java.util.Iterator;
import com.kingdee.util.enums.StringEnum;

/**
 * output class name
 */
public class ValueForMoneyEnum extends StringEnum
{
    public static final String EXCELLENT_VALUE = "10";
    public static final String GOOD_VALUE = "20";
    public static final String IN_VALUE = "30";
    public static final String POOR_VALUE = "40";

    public static final ValueForMoneyEnum Excellent = new ValueForMoneyEnum("Excellent", EXCELLENT_VALUE);
    public static final ValueForMoneyEnum Good = new ValueForMoneyEnum("Good", GOOD_VALUE);
    public static final ValueForMoneyEnum In = new ValueForMoneyEnum("In", IN_VALUE);
    public static final ValueForMoneyEnum Poor = new ValueForMoneyEnum("Poor", POOR_VALUE);

    /**
     * construct function
     * @param String valueForMoneyEnum
     */
    private ValueForMoneyEnum(String name, String valueForMoneyEnum)
    {
        super(name, valueForMoneyEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static ValueForMoneyEnum getEnum(String valueForMoneyEnum)
    {
        return (ValueForMoneyEnum)getEnum(ValueForMoneyEnum.class, valueForMoneyEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(ValueForMoneyEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(ValueForMoneyEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(ValueForMoneyEnum.class);
    }
}