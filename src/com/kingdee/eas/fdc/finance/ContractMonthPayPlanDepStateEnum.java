/**
 * output package name
 */
package com.kingdee.eas.fdc.finance;

import java.util.Map;
import java.util.List;
import java.util.Iterator;
import com.kingdee.util.enums.StringEnum;

/**
 * output class name
 */
public class ContractMonthPayPlanDepStateEnum extends StringEnum
{
    public static final String ALL_VALUE = "0ALL";
    public static final String SAVE_VALUE = "1SAVED";
    public static final String SUBMIT_VALUE = "2SUBMITTED";
    public static final String AUDITING_VALUE = "3AUDITTING";
    public static final String AUDITED_VALUE = "4AUDITTED";
    public static final String REPORTED_VALUE = "10PUBLISH";
    public static final String BACKED_VALUE = "11BACK";

    public static final ContractMonthPayPlanDepStateEnum all = new ContractMonthPayPlanDepStateEnum("all", ALL_VALUE);
    public static final ContractMonthPayPlanDepStateEnum save = new ContractMonthPayPlanDepStateEnum("save", SAVE_VALUE);
    public static final ContractMonthPayPlanDepStateEnum submit = new ContractMonthPayPlanDepStateEnum("submit", SUBMIT_VALUE);
    public static final ContractMonthPayPlanDepStateEnum auditing = new ContractMonthPayPlanDepStateEnum("auditing", AUDITING_VALUE);
    public static final ContractMonthPayPlanDepStateEnum audited = new ContractMonthPayPlanDepStateEnum("audited", AUDITED_VALUE);
    public static final ContractMonthPayPlanDepStateEnum reported = new ContractMonthPayPlanDepStateEnum("reported", REPORTED_VALUE);
    public static final ContractMonthPayPlanDepStateEnum backed = new ContractMonthPayPlanDepStateEnum("backed", BACKED_VALUE);

    /**
     * construct function
     * @param String contractMonthPayPlanDepStateEnum
     */
    private ContractMonthPayPlanDepStateEnum(String name, String contractMonthPayPlanDepStateEnum)
    {
        super(name, contractMonthPayPlanDepStateEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static ContractMonthPayPlanDepStateEnum getEnum(String contractMonthPayPlanDepStateEnum)
    {
        return (ContractMonthPayPlanDepStateEnum)getEnum(ContractMonthPayPlanDepStateEnum.class, contractMonthPayPlanDepStateEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(ContractMonthPayPlanDepStateEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(ContractMonthPayPlanDepStateEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(ContractMonthPayPlanDepStateEnum.class);
    }
}