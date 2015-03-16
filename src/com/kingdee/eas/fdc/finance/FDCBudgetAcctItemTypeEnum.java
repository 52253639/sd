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
public class FDCBudgetAcctItemTypeEnum extends StringEnum
{
    public static final String CONTRACT_VALUE = "1CONTRACT";
    public static final String CONWITHOUTTEXT_VALUE = "5CONWITHOUTTEXT";
    public static final String UNSETTLEDCONTRACT_VALUE = "9UNSETTLEDCONTRACT";

    public static final FDCBudgetAcctItemTypeEnum CONTRACT = new FDCBudgetAcctItemTypeEnum("CONTRACT", CONTRACT_VALUE);
    public static final FDCBudgetAcctItemTypeEnum CONWITHOUTTEXT = new FDCBudgetAcctItemTypeEnum("CONWITHOUTTEXT", CONWITHOUTTEXT_VALUE);
    public static final FDCBudgetAcctItemTypeEnum UNSETTLEDCONTRACT = new FDCBudgetAcctItemTypeEnum("UNSETTLEDCONTRACT", UNSETTLEDCONTRACT_VALUE);

    /**
     * construct function
     * @param String fDCBudgetAcctItemTypeEnum
     */
    private FDCBudgetAcctItemTypeEnum(String name, String fDCBudgetAcctItemTypeEnum)
    {
        super(name, fDCBudgetAcctItemTypeEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static FDCBudgetAcctItemTypeEnum getEnum(String fDCBudgetAcctItemTypeEnum)
    {
        return (FDCBudgetAcctItemTypeEnum)getEnum(FDCBudgetAcctItemTypeEnum.class, fDCBudgetAcctItemTypeEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(FDCBudgetAcctItemTypeEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(FDCBudgetAcctItemTypeEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(FDCBudgetAcctItemTypeEnum.class);
    }
}