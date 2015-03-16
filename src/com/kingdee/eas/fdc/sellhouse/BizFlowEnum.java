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
public class BizFlowEnum extends StringEnum
{
    public static final String PURCHASE_VALUE = "Purchase";
    public static final String SIGN_VALUE = "Sign";
    public static final String ARAACOMPENSATION_VALUE = "AraaCompensation";
    public static final String PAYFIRSTTERM_VALUE = "PayFirstTerm";
    public static final String JOININ_VALUE = "JoinIn";
    public static final String LOAN_VALUE = "Loan";
    public static final String ACCFUND_VALUE = "AccFund";
    public static final String LOANINACCT_VALUE = "LoanInAcct";
    public static final String ACCFUNDINACCT_VALUE = "AccFundInAcct";
    public static final String PROPERTYBOOK_VALUE = "PropertyBook";
    public static final String PAYALL_VALUE = "PayAll";

    public static final BizFlowEnum Purchase = new BizFlowEnum("Purchase", PURCHASE_VALUE);
    public static final BizFlowEnum Sign = new BizFlowEnum("Sign", SIGN_VALUE);
    public static final BizFlowEnum AraaCompensation = new BizFlowEnum("AraaCompensation", ARAACOMPENSATION_VALUE);
    public static final BizFlowEnum PayFirstTerm = new BizFlowEnum("PayFirstTerm", PAYFIRSTTERM_VALUE);
    public static final BizFlowEnum JoinIn = new BizFlowEnum("JoinIn", JOININ_VALUE);
    public static final BizFlowEnum Loan = new BizFlowEnum("Loan", LOAN_VALUE);
    public static final BizFlowEnum AccFund = new BizFlowEnum("AccFund", ACCFUND_VALUE);
    public static final BizFlowEnum LoanInAcct = new BizFlowEnum("LoanInAcct", LOANINACCT_VALUE);
    public static final BizFlowEnum AccFundInAcct = new BizFlowEnum("AccFundInAcct", ACCFUNDINACCT_VALUE);
    public static final BizFlowEnum PropertyBook = new BizFlowEnum("PropertyBook", PROPERTYBOOK_VALUE);
    public static final BizFlowEnum PayAll = new BizFlowEnum("PayAll", PAYALL_VALUE);

    /**
     * construct function
     * @param String bizFlowEnum
     */
    private BizFlowEnum(String name, String bizFlowEnum)
    {
        super(name, bizFlowEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static BizFlowEnum getEnum(String bizFlowEnum)
    {
        return (BizFlowEnum)getEnum(BizFlowEnum.class, bizFlowEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(BizFlowEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(BizFlowEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(BizFlowEnum.class);
    }
}