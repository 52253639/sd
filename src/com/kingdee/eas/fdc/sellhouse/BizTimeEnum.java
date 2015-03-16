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
public class BizTimeEnum extends StringEnum
{
    public static final String PURCHASE_VALUE = "Purchase";
    public static final String APPTIME_VALUE = "AppTime";

    public static final BizTimeEnum Purchase = new BizTimeEnum("Purchase", PURCHASE_VALUE);
    public static final BizTimeEnum AppTime = new BizTimeEnum("AppTime", APPTIME_VALUE);

    /**
     * construct function
     * @param String bizTimeEnum
     */
    private BizTimeEnum(String name, String bizTimeEnum)
    {
        super(name, bizTimeEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static BizTimeEnum getEnum(String bizTimeEnum)
    {
        return (BizTimeEnum)getEnum(BizTimeEnum.class, bizTimeEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(BizTimeEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(BizTimeEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(BizTimeEnum.class);
    }
}