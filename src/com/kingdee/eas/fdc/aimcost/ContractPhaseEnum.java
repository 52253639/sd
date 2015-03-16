/**
 * output package name
 */
package com.kingdee.eas.fdc.aimcost;

import java.util.Map;
import java.util.List;
import java.util.Iterator;
import com.kingdee.util.enums.StringEnum;

/**
 * output class name
 */
public class ContractPhaseEnum extends StringEnum
{
    public static final String SIGN_VALUE = "SIGN";
    public static final String SETTLE_VALUE = "SETTLE";
    public static final String BUDGET_VALUE = "BUDGET";

    public static final ContractPhaseEnum SIGN = new ContractPhaseEnum("SIGN", SIGN_VALUE);
    public static final ContractPhaseEnum SETTLE = new ContractPhaseEnum("SETTLE", SETTLE_VALUE);
    public static final ContractPhaseEnum BUDGET = new ContractPhaseEnum("BUDGET", BUDGET_VALUE);

    /**
     * construct function
     * @param String contractPhaseEnum
     */
    private ContractPhaseEnum(String name, String contractPhaseEnum)
    {
        super(name, contractPhaseEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static ContractPhaseEnum getEnum(String contractPhaseEnum)
    {
        return (ContractPhaseEnum)getEnum(ContractPhaseEnum.class, contractPhaseEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(ContractPhaseEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(ContractPhaseEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(ContractPhaseEnum.class);
    }
}