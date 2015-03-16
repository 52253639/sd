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
public class RelatBizType extends StringEnum
{
    public static final String PREORDER_VALUE = "PreOrder";
    public static final String PREPUR_VALUE = "PrePur";
    public static final String PURCHASE_VALUE = "Purchase";
    public static final String SIGN_VALUE = "Sign";
    public static final String CHANGE_VALUE = "Change";

    public static final RelatBizType PreOrder = new RelatBizType("PreOrder", PREORDER_VALUE);
    public static final RelatBizType PrePur = new RelatBizType("PrePur", PREPUR_VALUE);
    public static final RelatBizType Purchase = new RelatBizType("Purchase", PURCHASE_VALUE);
    public static final RelatBizType Sign = new RelatBizType("Sign", SIGN_VALUE);
    public static final RelatBizType Change = new RelatBizType("Change", CHANGE_VALUE);

    /**
     * construct function
     * @param String relatBizType
     */
    private RelatBizType(String name, String relatBizType)
    {
        super(name, relatBizType);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static RelatBizType getEnum(String relatBizType)
    {
        return (RelatBizType)getEnum(RelatBizType.class, relatBizType);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(RelatBizType.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(RelatBizType.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(RelatBizType.class);
    }
}