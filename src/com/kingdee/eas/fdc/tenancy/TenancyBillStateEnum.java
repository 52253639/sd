/**
 * output package name
 */
package com.kingdee.eas.fdc.tenancy;

import java.util.Map;
import java.util.List;
import java.util.Iterator;
import com.kingdee.util.enums.StringEnum;

/**
 * output class name
 */
public class TenancyBillStateEnum extends StringEnum
{
    public static final String SAVED_VALUE = "Saved";
    public static final String SUBMITTED_VALUE = "Submitted";
    public static final String AUDITING_VALUE = "Auditing";
    public static final String AUDITED_VALUE = "Audited";
    public static final String PARTEXECUTED_VALUE = "PartExecuted";
    public static final String EXECUTING_VALUE = "Executing";
    public static final String CONTINUETENANCYING_VALUE = "ContinueTenancying";
    public static final String REJIGGERTENANCYING_VALUE = "RejiggerTenancying";
    public static final String CHANGENAMING_VALUE = "ChangeNaming";
    public static final String TENANCYCHANGING_VALUE = "TenancyChanging";
    public static final String QUITTENANCYING_VALUE = "QuitTenancying";
    public static final String EXPIRATION_VALUE = "Expiration";
    public static final String BLANKOUT_VALUE = "BlankOut";
    public static final String PRICECHANGING_VALUE = "PriceChanging";

    public static final TenancyBillStateEnum Saved = new TenancyBillStateEnum("Saved", SAVED_VALUE);
    public static final TenancyBillStateEnum Submitted = new TenancyBillStateEnum("Submitted", SUBMITTED_VALUE);
    public static final TenancyBillStateEnum Auditing = new TenancyBillStateEnum("Auditing", AUDITING_VALUE);
    public static final TenancyBillStateEnum Audited = new TenancyBillStateEnum("Audited", AUDITED_VALUE);
    public static final TenancyBillStateEnum PartExecuted = new TenancyBillStateEnum("PartExecuted", PARTEXECUTED_VALUE);
    public static final TenancyBillStateEnum Executing = new TenancyBillStateEnum("Executing", EXECUTING_VALUE);
    public static final TenancyBillStateEnum ContinueTenancying = new TenancyBillStateEnum("ContinueTenancying", CONTINUETENANCYING_VALUE);
    public static final TenancyBillStateEnum RejiggerTenancying = new TenancyBillStateEnum("RejiggerTenancying", REJIGGERTENANCYING_VALUE);
    public static final TenancyBillStateEnum ChangeNaming = new TenancyBillStateEnum("ChangeNaming", CHANGENAMING_VALUE);
    public static final TenancyBillStateEnum TenancyChanging = new TenancyBillStateEnum("TenancyChanging", TENANCYCHANGING_VALUE);
    public static final TenancyBillStateEnum QuitTenancying = new TenancyBillStateEnum("QuitTenancying", QUITTENANCYING_VALUE);
    public static final TenancyBillStateEnum Expiration = new TenancyBillStateEnum("Expiration", EXPIRATION_VALUE);
    public static final TenancyBillStateEnum BlankOut = new TenancyBillStateEnum("BlankOut", BLANKOUT_VALUE);
    public static final TenancyBillStateEnum PriceChanging = new TenancyBillStateEnum("PriceChanging", PRICECHANGING_VALUE);

    /**
     * construct function
     * @param String tenancyBillStateEnum
     */
    private TenancyBillStateEnum(String name, String tenancyBillStateEnum)
    {
        super(name, tenancyBillStateEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static TenancyBillStateEnum getEnum(String tenancyBillStateEnum)
    {
        return (TenancyBillStateEnum)getEnum(TenancyBillStateEnum.class, tenancyBillStateEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(TenancyBillStateEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(TenancyBillStateEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(TenancyBillStateEnum.class);
    }
}