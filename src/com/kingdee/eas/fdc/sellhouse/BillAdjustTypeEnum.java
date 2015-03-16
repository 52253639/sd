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
public class BillAdjustTypeEnum extends StringEnum
{
    public static final String PURCHASE_VALUE = "Purchase";

    public static final BillAdjustTypeEnum Purchase = new BillAdjustTypeEnum("Purchase", PURCHASE_VALUE);

    /**
     * construct function
     * @param String billAdjustTypeEnum
     */
    private BillAdjustTypeEnum(String name, String billAdjustTypeEnum)
    {
        super(name, billAdjustTypeEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static BillAdjustTypeEnum getEnum(String billAdjustTypeEnum)
    {
        return (BillAdjustTypeEnum)getEnum(BillAdjustTypeEnum.class, billAdjustTypeEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(BillAdjustTypeEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(BillAdjustTypeEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(BillAdjustTypeEnum.class);
    }
}