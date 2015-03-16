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
public class SincerityPurchaseStateEnum extends StringEnum
{
    public static final String ARRANGENUM_VALUE = "ArrangeNum";
    public static final String QUITNUM_VALUE = "QuitNum";
    public static final String TOPURCHASE_VALUE = "ToPurchase";
    public static final String INVALID_VALUE = "Invalid";

    public static final SincerityPurchaseStateEnum ArrangeNum = new SincerityPurchaseStateEnum("ArrangeNum", ARRANGENUM_VALUE);
    public static final SincerityPurchaseStateEnum QuitNum = new SincerityPurchaseStateEnum("QuitNum", QUITNUM_VALUE);
    public static final SincerityPurchaseStateEnum ToPurchase = new SincerityPurchaseStateEnum("ToPurchase", TOPURCHASE_VALUE);
    public static final SincerityPurchaseStateEnum Invalid = new SincerityPurchaseStateEnum("Invalid", INVALID_VALUE);

    /**
     * construct function
     * @param String sincerityPurchaseStateEnum
     */
    private SincerityPurchaseStateEnum(String name, String sincerityPurchaseStateEnum)
    {
        super(name, sincerityPurchaseStateEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static SincerityPurchaseStateEnum getEnum(String sincerityPurchaseStateEnum)
    {
        return (SincerityPurchaseStateEnum)getEnum(SincerityPurchaseStateEnum.class, sincerityPurchaseStateEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(SincerityPurchaseStateEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(SincerityPurchaseStateEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(SincerityPurchaseStateEnum.class);
    }
}