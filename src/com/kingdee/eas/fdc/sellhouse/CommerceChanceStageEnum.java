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
public class CommerceChanceStageEnum extends StringEnum
{
    public static final String SINCERPURCHASE_VALUE = "SincerPurchase";
    public static final String PREPURCHASE_VALUE = "PrePurchase";
    public static final String PURCHASE_VALUE = "Purchase";
    public static final String SIGN_VALUE = "Sign";
    public static final String CHANGENAME_VALUE = "ChangeName";
    public static final String QUITROOM_VALUE = "QuitRoom";

    public static final CommerceChanceStageEnum SincerPurchase = new CommerceChanceStageEnum("SincerPurchase", SINCERPURCHASE_VALUE);
    public static final CommerceChanceStageEnum PrePurchase = new CommerceChanceStageEnum("PrePurchase", PREPURCHASE_VALUE);
    public static final CommerceChanceStageEnum Purchase = new CommerceChanceStageEnum("Purchase", PURCHASE_VALUE);
    public static final CommerceChanceStageEnum Sign = new CommerceChanceStageEnum("Sign", SIGN_VALUE);
    public static final CommerceChanceStageEnum ChangeName = new CommerceChanceStageEnum("ChangeName", CHANGENAME_VALUE);
    public static final CommerceChanceStageEnum QuitRoom = new CommerceChanceStageEnum("QuitRoom", QUITROOM_VALUE);

    /**
     * construct function
     * @param String commerceChanceStageEnum
     */
    private CommerceChanceStageEnum(String name, String commerceChanceStageEnum)
    {
        super(name, commerceChanceStageEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static CommerceChanceStageEnum getEnum(String commerceChanceStageEnum)
    {
        return (CommerceChanceStageEnum)getEnum(CommerceChanceStageEnum.class, commerceChanceStageEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(CommerceChanceStageEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(CommerceChanceStageEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(CommerceChanceStageEnum.class);
    }
}