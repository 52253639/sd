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
public class BizStateEnum extends StringEnum
{
    public static final String INVALID_VALUE = "1INVALID";
    public static final String SUBMIT_VALUE = "2SUBMIT";
    public static final String CANCEL_VALUE = "3CANCEL";
    public static final String AUDITTED_VALUE = "4AUDITTED";
    public static final String SINCEROBLIGATED_VALUE = "5SINCEROBLIGATED";
    public static final String TENANCY_VALUE = "6TENANCY";
    public static final String SAVE_VALUE = "7SAVE";
    public static final String LEASE_VALUE = "8LEASE";

    public static final BizStateEnum INVALID = new BizStateEnum("INVALID", INVALID_VALUE);
    public static final BizStateEnum SUBMIT = new BizStateEnum("SUBMIT", SUBMIT_VALUE);
    public static final BizStateEnum CANCEL = new BizStateEnum("CANCEL", CANCEL_VALUE);
    public static final BizStateEnum AUDITTED = new BizStateEnum("AUDITTED", AUDITTED_VALUE);
    public static final BizStateEnum SINCEROBLIGATED = new BizStateEnum("SINCEROBLIGATED", SINCEROBLIGATED_VALUE);
    public static final BizStateEnum TENANCY = new BizStateEnum("TENANCY", TENANCY_VALUE);
    public static final BizStateEnum SAVE = new BizStateEnum("SAVE", SAVE_VALUE);
    public static final BizStateEnum LEASE = new BizStateEnum("LEASE", LEASE_VALUE);

    /**
     * construct function
     * @param String bizStateEnum
     */
    private BizStateEnum(String name, String bizStateEnum)
    {
        super(name, bizStateEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static BizStateEnum getEnum(String bizStateEnum)
    {
        return (BizStateEnum)getEnum(BizStateEnum.class, bizStateEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(BizStateEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(BizStateEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(BizStateEnum.class);
    }
}