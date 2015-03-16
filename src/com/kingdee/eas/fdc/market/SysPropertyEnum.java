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
public class SysPropertyEnum extends StringEnum
{
    public static final String SALEPROPERTY_VALUE = "SHE";
    public static final String TENANCYPROPERTY_VALUE = "TEN";
    public static final String PPMPROPERTY_VALUE = "PPM";

    public static final SysPropertyEnum SaleProperty = new SysPropertyEnum("SaleProperty", SALEPROPERTY_VALUE);
    public static final SysPropertyEnum TenancyProperty = new SysPropertyEnum("TenancyProperty", TENANCYPROPERTY_VALUE);
    public static final SysPropertyEnum PpmProperty = new SysPropertyEnum("PpmProperty", PPMPROPERTY_VALUE);

    /**
     * construct function
     * @param String sysPropertyEnum
     */
    private SysPropertyEnum(String name, String sysPropertyEnum)
    {
        super(name, sysPropertyEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static SysPropertyEnum getEnum(String sysPropertyEnum)
    {
        return (SysPropertyEnum)getEnum(SysPropertyEnum.class, sysPropertyEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(SysPropertyEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(SysPropertyEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(SysPropertyEnum.class);
    }
}