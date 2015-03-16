/**
 * output package name
 */
package com.kingdee.eas.fdc.basecrm;

import java.util.Map;
import java.util.List;
import java.util.Iterator;
import com.kingdee.util.enums.StringEnum;

/**
 * output class name
 */
public class BizBillType extends StringEnum
{
    public static final String PREORDER_VALUE = "preOrder";
    public static final String PREPUR_VALUE = "prePur";
    public static final String PURCHASE_VALUE = "purchase";
    public static final String SIGN_VALUE = "sign";

    public static final BizBillType preOrder = new BizBillType("preOrder", PREORDER_VALUE);
    public static final BizBillType prePur = new BizBillType("prePur", PREPUR_VALUE);
    public static final BizBillType purchase = new BizBillType("purchase", PURCHASE_VALUE);
    public static final BizBillType sign = new BizBillType("sign", SIGN_VALUE);

    /**
     * construct function
     * @param String bizBillType
     */
    private BizBillType(String name, String bizBillType)
    {
        super(name, bizBillType);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static BizBillType getEnum(String bizBillType)
    {
        return (BizBillType)getEnum(BizBillType.class, bizBillType);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(BizBillType.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(BizBillType.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(BizBillType.class);
    }
}