/**
 * output package name
 */
package com.kingdee.eas.fdc.schedule;

import java.util.Map;
import java.util.List;
import java.util.Iterator;
import com.kingdee.util.enums.StringEnum;

/**
 * output class name
 */
public class AdjustReqGattWBSEnum extends StringEnum
{
    public static final String ADDNEW_VALUE = "1ADDNEW";
    public static final String DELETE_VALUE = "2DELETE";

    public static final AdjustReqGattWBSEnum ADDNEW = new AdjustReqGattWBSEnum("ADDNEW", ADDNEW_VALUE);
    public static final AdjustReqGattWBSEnum DELETE = new AdjustReqGattWBSEnum("DELETE", DELETE_VALUE);

    /**
     * construct function
     * @param String adjustReqGattWBSEnum
     */
    private AdjustReqGattWBSEnum(String name, String adjustReqGattWBSEnum)
    {
        super(name, adjustReqGattWBSEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static AdjustReqGattWBSEnum getEnum(String adjustReqGattWBSEnum)
    {
        return (AdjustReqGattWBSEnum)getEnum(AdjustReqGattWBSEnum.class, adjustReqGattWBSEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(AdjustReqGattWBSEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(AdjustReqGattWBSEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(AdjustReqGattWBSEnum.class);
    }
}