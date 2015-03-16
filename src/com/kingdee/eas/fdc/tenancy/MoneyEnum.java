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
public class MoneyEnum extends StringEnum
{
    public static final String YUAN_VALUE = "10";
    public static final String JIAO_VALUE = "20";
    public static final String FEN_VALUE = "30";

    public static final MoneyEnum YUAN = new MoneyEnum("YUAN", YUAN_VALUE);
    public static final MoneyEnum JIAO = new MoneyEnum("JIAO", JIAO_VALUE);
    public static final MoneyEnum FEN = new MoneyEnum("FEN", FEN_VALUE);

    /**
     * construct function
     * @param String moneyEnum
     */
    private MoneyEnum(String name, String moneyEnum)
    {
        super(name, moneyEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static MoneyEnum getEnum(String moneyEnum)
    {
        return (MoneyEnum)getEnum(MoneyEnum.class, moneyEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(MoneyEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(MoneyEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(MoneyEnum.class);
    }
}