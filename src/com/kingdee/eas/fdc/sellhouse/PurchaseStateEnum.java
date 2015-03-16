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
public class PurchaseStateEnum extends StringEnum
{
    public static final String PREPURCHASEAPPLY_VALUE = "PrePurchaseApply";
    public static final String PREPURCHASECHECK_VALUE = "PrePurchaseCheck";
    public static final String PURCHASEAPPLY_VALUE = "PurchaseApply";
    public static final String PURCHASEAUDITING_VALUE = "PurchaseAuditing";
    public static final String PURCHASEAUDIT_VALUE = "PurchaseAudit";
    public static final String PURCHASECHANGE_VALUE = "PurchaseChange";
    public static final String CHANGEROOMBLANKOUT_VALUE = "ChangeRoomBlankOut";
    public static final String QUITROOMBLANKOUT_VALUE = "QuitRoomBlankOut";
    public static final String NOPAYBLANKOUT_VALUE = "NoPayBlankOut";
    public static final String MANUALBLANKOUT_VALUE = "ManualBlankOut";
    public static final String ADJUSTBLANKOUT_VALUE = "AdjustBlankOut";

    public static final PurchaseStateEnum PrePurchaseApply = new PurchaseStateEnum("PrePurchaseApply", PREPURCHASEAPPLY_VALUE);
    public static final PurchaseStateEnum PrePurchaseCheck = new PurchaseStateEnum("PrePurchaseCheck", PREPURCHASECHECK_VALUE);
    public static final PurchaseStateEnum PurchaseApply = new PurchaseStateEnum("PurchaseApply", PURCHASEAPPLY_VALUE);
    public static final PurchaseStateEnum PurchaseAuditing = new PurchaseStateEnum("PurchaseAuditing", PURCHASEAUDITING_VALUE);
    public static final PurchaseStateEnum PurchaseAudit = new PurchaseStateEnum("PurchaseAudit", PURCHASEAUDIT_VALUE);
    public static final PurchaseStateEnum PurchaseChange = new PurchaseStateEnum("PurchaseChange", PURCHASECHANGE_VALUE);
    public static final PurchaseStateEnum ChangeRoomBlankOut = new PurchaseStateEnum("ChangeRoomBlankOut", CHANGEROOMBLANKOUT_VALUE);
    public static final PurchaseStateEnum QuitRoomBlankOut = new PurchaseStateEnum("QuitRoomBlankOut", QUITROOMBLANKOUT_VALUE);
    public static final PurchaseStateEnum NoPayBlankOut = new PurchaseStateEnum("NoPayBlankOut", NOPAYBLANKOUT_VALUE);
    public static final PurchaseStateEnum ManualBlankOut = new PurchaseStateEnum("ManualBlankOut", MANUALBLANKOUT_VALUE);
    public static final PurchaseStateEnum AdjustBlankOut = new PurchaseStateEnum("AdjustBlankOut", ADJUSTBLANKOUT_VALUE);

    /**
     * construct function
     * @param String purchaseStateEnum
     */
    private PurchaseStateEnum(String name, String purchaseStateEnum)
    {
        super(name, purchaseStateEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static PurchaseStateEnum getEnum(String purchaseStateEnum)
    {
        return (PurchaseStateEnum)getEnum(PurchaseStateEnum.class, purchaseStateEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(PurchaseStateEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(PurchaseStateEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(PurchaseStateEnum.class);
    }
}