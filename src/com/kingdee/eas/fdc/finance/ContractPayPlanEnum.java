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
public class ContractPayPlanEnum extends StringEnum
{
    public static final String SAVE_VALUE = "save";
    public static final String SUMBIT_VALUE = "sumbit";
    public static final String AUDITING_VALUE = "auditing";
    public static final String AUDITED_VALUE = "audited";
    public static final String EMENING_VALUE = "emening";
    public static final String EMEND_VALUE = "emend";

    public static final ContractPayPlanEnum save = new ContractPayPlanEnum("save", SAVE_VALUE);
    public static final ContractPayPlanEnum sumbit = new ContractPayPlanEnum("sumbit", SUMBIT_VALUE);
    public static final ContractPayPlanEnum auditing = new ContractPayPlanEnum("auditing", AUDITING_VALUE);
    public static final ContractPayPlanEnum audited = new ContractPayPlanEnum("audited", AUDITED_VALUE);
    public static final ContractPayPlanEnum emening = new ContractPayPlanEnum("emening", EMENING_VALUE);
    public static final ContractPayPlanEnum emend = new ContractPayPlanEnum("emend", EMEND_VALUE);

    /**
     * construct function
     * @param String contractPayPlanEnum
     */
    private ContractPayPlanEnum(String name, String contractPayPlanEnum)
    {
        super(name, contractPayPlanEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static ContractPayPlanEnum getEnum(String contractPayPlanEnum)
    {
        return (ContractPayPlanEnum)getEnum(ContractPayPlanEnum.class, contractPayPlanEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(ContractPayPlanEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(ContractPayPlanEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(ContractPayPlanEnum.class);
    }
}