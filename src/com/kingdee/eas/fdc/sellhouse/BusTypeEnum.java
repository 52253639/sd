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
public class BusTypeEnum extends StringEnum
{
    public static final String PAY_VALUE = "Pay";
    public static final String BUSINESS_VALUE = "Business";
    public static final String OTHER_VALUE = "Other";

    public static final BusTypeEnum PAY = new BusTypeEnum("PAY", PAY_VALUE);
    public static final BusTypeEnum BUSINESS = new BusTypeEnum("BUSINESS", BUSINESS_VALUE);
    public static final BusTypeEnum OTHER = new BusTypeEnum("OTHER", OTHER_VALUE);

    /**
     * construct function
     * @param String busTypeEnum
     */
    private BusTypeEnum(String name, String busTypeEnum)
    {
        super(name, busTypeEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static BusTypeEnum getEnum(String busTypeEnum)
    {
        return (BusTypeEnum)getEnum(BusTypeEnum.class, busTypeEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(BusTypeEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(BusTypeEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(BusTypeEnum.class);
    }
}