/**
 * output package name
 */
package com.kingdee.eas.fdc.aimcost;

import java.util.Map;
import java.util.List;
import java.util.Iterator;
import com.kingdee.util.enums.StringEnum;

/**
 * output class name
 */
public class FieldTypeEnum extends StringEnum
{
    public static final String TEXT_VALUE = "TEXT";
    public static final String DIGITAL_VALUE = "DIGITAL";
    public static final String TIME_VALUE = "TIME";
    public static final String PRODUCTTYPE_VALUE = "PRODUCTTYPE";
    public static final String UNIT_VALUE = "UNIT";

    public static final FieldTypeEnum TEXT = new FieldTypeEnum("TEXT", TEXT_VALUE);
    public static final FieldTypeEnum DIGITAL = new FieldTypeEnum("DIGITAL", DIGITAL_VALUE);
    public static final FieldTypeEnum TIME = new FieldTypeEnum("TIME", TIME_VALUE);
    public static final FieldTypeEnum PRODUCTTYPE = new FieldTypeEnum("PRODUCTTYPE", PRODUCTTYPE_VALUE);
    public static final FieldTypeEnum UNIT = new FieldTypeEnum("UNIT", UNIT_VALUE);

    /**
     * construct function
     * @param String fieldTypeEnum
     */
    private FieldTypeEnum(String name, String fieldTypeEnum)
    {
        super(name, fieldTypeEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static FieldTypeEnum getEnum(String fieldTypeEnum)
    {
        return (FieldTypeEnum)getEnum(FieldTypeEnum.class, fieldTypeEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(FieldTypeEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(FieldTypeEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(FieldTypeEnum.class);
    }
}