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
public class BaseTypeEnum extends StringEnum
{
    public static final String BUILDINGAREA_VALUE = "buildingArea";
    public static final String ROOMAREA_VALUE = "roomArea";
    public static final String STANDARDAMOUNT_VALUE = "standardAmount";

    public static final BaseTypeEnum BUILDINGAREA = new BaseTypeEnum("BUILDINGAREA", BUILDINGAREA_VALUE);
    public static final BaseTypeEnum ROOMAREA = new BaseTypeEnum("ROOMAREA", ROOMAREA_VALUE);
    public static final BaseTypeEnum STANDARDAMOUNT = new BaseTypeEnum("STANDARDAMOUNT", STANDARDAMOUNT_VALUE);

    /**
     * construct function
     * @param String baseTypeEnum
     */
    private BaseTypeEnum(String name, String baseTypeEnum)
    {
        super(name, baseTypeEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static BaseTypeEnum getEnum(String baseTypeEnum)
    {
        return (BaseTypeEnum)getEnum(BaseTypeEnum.class, baseTypeEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(BaseTypeEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(BaseTypeEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(BaseTypeEnum.class);
    }
}