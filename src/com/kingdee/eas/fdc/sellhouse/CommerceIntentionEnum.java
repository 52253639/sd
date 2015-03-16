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
public class CommerceIntentionEnum extends StringEnum
{
    public static final String UNKNOW_VALUE = "UnKnow";
    public static final String INVEST_VALUE = "Invest";
    public static final String OWNUSE_VALUE = "OwnUse";
    public static final String OWNUSEANDINVEST_VALUE = "OwnUseAndInvest";

    public static final CommerceIntentionEnum UnKnow = new CommerceIntentionEnum("UnKnow", UNKNOW_VALUE);
    public static final CommerceIntentionEnum Invest = new CommerceIntentionEnum("Invest", INVEST_VALUE);
    public static final CommerceIntentionEnum OwnUse = new CommerceIntentionEnum("OwnUse", OWNUSE_VALUE);
    public static final CommerceIntentionEnum OwnUseAndInvest = new CommerceIntentionEnum("OwnUseAndInvest", OWNUSEANDINVEST_VALUE);

    /**
     * construct function
     * @param String commerceIntentionEnum
     */
    private CommerceIntentionEnum(String name, String commerceIntentionEnum)
    {
        super(name, commerceIntentionEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static CommerceIntentionEnum getEnum(String commerceIntentionEnum)
    {
        return (CommerceIntentionEnum)getEnum(CommerceIntentionEnum.class, commerceIntentionEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(CommerceIntentionEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(CommerceIntentionEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(CommerceIntentionEnum.class);
    }
}