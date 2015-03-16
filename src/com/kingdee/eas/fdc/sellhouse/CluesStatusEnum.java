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
public class CluesStatusEnum extends StringEnum
{
    public static final String COMMERCECHANCE_VALUE = "1COMMERCECHANCE";
    public static final String PREPURCHASE_VALUE = "2PREPURCHASE";
    public static final String PURCHASE_VALUE = "3PURCHASE";
    public static final String SIGN_VALUE = "4SIGN";
    public static final String CUSTOMER_VALUE = "5CUSTOMER";

    public static final CluesStatusEnum COMMERCECHANCE = new CluesStatusEnum("COMMERCECHANCE", COMMERCECHANCE_VALUE);
    public static final CluesStatusEnum PREPURCHASE = new CluesStatusEnum("PREPURCHASE", PREPURCHASE_VALUE);
    public static final CluesStatusEnum PURCHASE = new CluesStatusEnum("PURCHASE", PURCHASE_VALUE);
    public static final CluesStatusEnum SIGN = new CluesStatusEnum("SIGN", SIGN_VALUE);
    public static final CluesStatusEnum CUSTOMER = new CluesStatusEnum("CUSTOMER", CUSTOMER_VALUE);

    /**
     * construct function
     * @param String cluesStatusEnum
     */
    private CluesStatusEnum(String name, String cluesStatusEnum)
    {
        super(name, cluesStatusEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static CluesStatusEnum getEnum(String cluesStatusEnum)
    {
        return (CluesStatusEnum)getEnum(CluesStatusEnum.class, cluesStatusEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(CluesStatusEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(CluesStatusEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(CluesStatusEnum.class);
    }
}