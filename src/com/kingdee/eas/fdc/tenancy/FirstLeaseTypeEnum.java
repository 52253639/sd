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
public class FirstLeaseTypeEnum extends StringEnum
{
    public static final String WHOLEFIRSTLEASE_VALUE = "WholeFirstLease";
    public static final String USERDEFINED_VALUE = "UserDefined";

    public static final FirstLeaseTypeEnum WholeFirstLease = new FirstLeaseTypeEnum("WholeFirstLease", WHOLEFIRSTLEASE_VALUE);
    public static final FirstLeaseTypeEnum UserDefined = new FirstLeaseTypeEnum("UserDefined", USERDEFINED_VALUE);

    /**
     * construct function
     * @param String firstLeaseTypeEnum
     */
    private FirstLeaseTypeEnum(String name, String firstLeaseTypeEnum)
    {
        super(name, firstLeaseTypeEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static FirstLeaseTypeEnum getEnum(String firstLeaseTypeEnum)
    {
        return (FirstLeaseTypeEnum)getEnum(FirstLeaseTypeEnum.class, firstLeaseTypeEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(FirstLeaseTypeEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(FirstLeaseTypeEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(FirstLeaseTypeEnum.class);
    }
}