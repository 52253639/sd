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
public class SellProjectResourceEnum extends StringEnum
{
    public static final String DEVELOPER_VALUE = "1DEVELOPER";
    public static final String INVESTOR_VALUE = "2INVESTOR";
    public static final String CONSIGN_VALUE = "2CONSIGN";

    public static final SellProjectResourceEnum DEVELOPER = new SellProjectResourceEnum("DEVELOPER", DEVELOPER_VALUE);
    public static final SellProjectResourceEnum INVESTOR = new SellProjectResourceEnum("INVESTOR", INVESTOR_VALUE);
    public static final SellProjectResourceEnum CONSIGN = new SellProjectResourceEnum("CONSIGN", CONSIGN_VALUE);

    /**
     * construct function
     * @param String sellProjectResourceEnum
     */
    private SellProjectResourceEnum(String name, String sellProjectResourceEnum)
    {
        super(name, sellProjectResourceEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static SellProjectResourceEnum getEnum(String sellProjectResourceEnum)
    {
        return (SellProjectResourceEnum)getEnum(SellProjectResourceEnum.class, sellProjectResourceEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(SellProjectResourceEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(SellProjectResourceEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(SellProjectResourceEnum.class);
    }
}