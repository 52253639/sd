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
public class AppPayDateTypeEnum extends StringEnum
{
    public static final String TENANCYAUDITEDDAY_VALUE = "TenancyAuditedDay";
    public static final String TENANCYDEPOSITREVDAY_VALUE = "TenancyDepositRevDay";
    public static final String TENANCYEXECUTEDAY_VALUE = "TenancyExecuteDay";

    public static final AppPayDateTypeEnum TenancyAuditedDay = new AppPayDateTypeEnum("TenancyAuditedDay", TENANCYAUDITEDDAY_VALUE);
    public static final AppPayDateTypeEnum TenancyDepositRevDay = new AppPayDateTypeEnum("TenancyDepositRevDay", TENANCYDEPOSITREVDAY_VALUE);
    public static final AppPayDateTypeEnum TenancyExecuteDay = new AppPayDateTypeEnum("TenancyExecuteDay", TENANCYEXECUTEDAY_VALUE);

    /**
     * construct function
     * @param String appPayDateTypeEnum
     */
    private AppPayDateTypeEnum(String name, String appPayDateTypeEnum)
    {
        super(name, appPayDateTypeEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static AppPayDateTypeEnum getEnum(String appPayDateTypeEnum)
    {
        return (AppPayDateTypeEnum)getEnum(AppPayDateTypeEnum.class, appPayDateTypeEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(AppPayDateTypeEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(AppPayDateTypeEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(AppPayDateTypeEnum.class);
    }
}