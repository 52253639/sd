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
public class CompensateTypeEnum extends StringEnum
{
    public static final String ROOMTYPE_VALUE = "1ROOMTYPE";
    public static final String BUILDINGAREA_VALUE = "2BUILDINGAREA";
    public static final String ROOMAREA_VALUE = "3ROOMAREA";

    public static final CompensateTypeEnum ROOMTYPE = new CompensateTypeEnum("ROOMTYPE", ROOMTYPE_VALUE);
    public static final CompensateTypeEnum BUILDINGAREA = new CompensateTypeEnum("BUILDINGAREA", BUILDINGAREA_VALUE);
    public static final CompensateTypeEnum ROOMAREA = new CompensateTypeEnum("ROOMAREA", ROOMAREA_VALUE);

    /**
     * construct function
     * @param String compensateTypeEnum
     */
    private CompensateTypeEnum(String name, String compensateTypeEnum)
    {
        super(name, compensateTypeEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static CompensateTypeEnum getEnum(String compensateTypeEnum)
    {
        return (CompensateTypeEnum)getEnum(CompensateTypeEnum.class, compensateTypeEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(CompensateTypeEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(CompensateTypeEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(CompensateTypeEnum.class);
    }
}