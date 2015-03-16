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
public class InstallTypeEnum extends StringEnum
{
    public static final String SUPPLIER_VALUE = "0SUPPLIER";
    public static final String CONSTRUCT_VALUE = "1CONSTRUCT";

    public static final InstallTypeEnum SUPPLIER = new InstallTypeEnum("SUPPLIER", SUPPLIER_VALUE);
    public static final InstallTypeEnum CONSTRUCT = new InstallTypeEnum("CONSTRUCT", CONSTRUCT_VALUE);

    /**
     * construct function
     * @param String installTypeEnum
     */
    private InstallTypeEnum(String name, String installTypeEnum)
    {
        super(name, installTypeEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static InstallTypeEnum getEnum(String installTypeEnum)
    {
        return (InstallTypeEnum)getEnum(InstallTypeEnum.class, installTypeEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(InstallTypeEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(InstallTypeEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(InstallTypeEnum.class);
    }
}