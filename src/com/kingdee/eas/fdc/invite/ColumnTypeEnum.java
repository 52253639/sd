/**
 * output package name
 */
package com.kingdee.eas.fdc.invite;

import java.util.Map;
import java.util.List;
import java.util.Iterator;
import com.kingdee.util.enums.StringEnum;

/**
 * output class name
 */
public class ColumnTypeEnum extends StringEnum
{
    public static final String AMOUNT_VALUE = "1AMOUNT";
    public static final String STRING_VALUE = "2STRING";
    public static final String DATE_VALUE = "3DATE";

    public static final ColumnTypeEnum Amount = new ColumnTypeEnum("Amount", AMOUNT_VALUE);
    public static final ColumnTypeEnum String = new ColumnTypeEnum("String", STRING_VALUE);
    public static final ColumnTypeEnum Date = new ColumnTypeEnum("Date", DATE_VALUE);

    /**
     * construct function
     * @param String columnTypeEnum
     */
    private ColumnTypeEnum(String name, String columnTypeEnum)
    {
        super(name, columnTypeEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static ColumnTypeEnum getEnum(String columnTypeEnum)
    {
        return (ColumnTypeEnum)getEnum(ColumnTypeEnum.class, columnTypeEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(ColumnTypeEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(ColumnTypeEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(ColumnTypeEnum.class);
    }
}