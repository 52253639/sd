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
public class MarketTypeEnum extends StringEnum
{
    public static final String VISITRATE_VALUE = "visitRate";
    public static final String VISIT_VALUE = "visit";
    public static final String PHONE_VALUE = "phone";

    public static final MarketTypeEnum visitRate = new MarketTypeEnum("visitRate", VISITRATE_VALUE);
    public static final MarketTypeEnum visit = new MarketTypeEnum("visit", VISIT_VALUE);
    public static final MarketTypeEnum phone = new MarketTypeEnum("phone", PHONE_VALUE);

    /**
     * construct function
     * @param String marketTypeEnum
     */
    private MarketTypeEnum(String name, String marketTypeEnum)
    {
        super(name, marketTypeEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static MarketTypeEnum getEnum(String marketTypeEnum)
    {
        return (MarketTypeEnum)getEnum(MarketTypeEnum.class, marketTypeEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(MarketTypeEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(MarketTypeEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(MarketTypeEnum.class);
    }
}