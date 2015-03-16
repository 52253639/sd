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
public class FactorEnum extends StringEnum
{
    public static final String DEALTOTALALMOUNT_VALUE = "DEALTOTALALMOUNT";
    public static final String STANDARTOATALMOUNT_VALUE = "STANDARTOATALMOUNT";
    public static final String BUILDINGAREA_VALUE = "BUILDINGAREA";
    public static final String ROOMAREA_VALUE = "ROOMAREA";

    public static final FactorEnum DEALTOTALALMOUNT = new FactorEnum("DEALTOTALALMOUNT", DEALTOTALALMOUNT_VALUE);
    public static final FactorEnum STANDARTOATALMOUNT = new FactorEnum("STANDARTOATALMOUNT", STANDARTOATALMOUNT_VALUE);
    public static final FactorEnum BUILDINGAREA = new FactorEnum("BUILDINGAREA", BUILDINGAREA_VALUE);
    public static final FactorEnum ROOMAREA = new FactorEnum("ROOMAREA", ROOMAREA_VALUE);

    /**
     * construct function
     * @param String factorEnum
     */
    private FactorEnum(String name, String factorEnum)
    {
        super(name, factorEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static FactorEnum getEnum(String factorEnum)
    {
        return (FactorEnum)getEnum(FactorEnum.class, factorEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(FactorEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(FactorEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(FactorEnum.class);
    }
}