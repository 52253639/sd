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
public class BusinessTypeNameEnum extends StringEnum
{
    public static final String PURCHASEAMOUNT_VALUE = "PurchaseAmount";
    public static final String FISRTAMOUNT_VALUE = "FisrtAmount";
    public static final String HOUSEAMOUNT_VALUE = "HouseAmount";
    public static final String LOANAMOUNT_VALUE = "LoanAmount";
    public static final String ACCFUNDAMOUNT_VALUE = "AccFundAmount";
    public static final String REPLACEFEE_VALUE = "ReplaceFee";
    public static final String PRELIMINARY_VALUE = "PreLiminary";
    public static final String PURCHASE_VALUE = "Purchase";
    public static final String SIGN_VALUE = "Sign";
    public static final String MORTGAGE_VALUE = "Mortgage";
    public static final String ACCFUND_VALUE = "AccFund";
    public static final String INTEREST_VALUE = "Interest";
    public static final String JOIN_VALUE = "Join";
    public static final String AREACOMPENSATE_VALUE = "AreaCompensate";
    public static final String SINCER_VALUE = "Sincer";
    public static final String PREMONEY_VALUE = "PreMoney";
    public static final String PRECONCERTMONEY_VALUE = "PreconcertMoney";
    public static final String REFUNDMENT_VALUE = "Refundment";
    public static final String FITMENTAMOUNT_VALUE = "FitmentAmount";
    public static final String LATEFEE_VALUE = "LateFee";
    public static final String COMPENSATEAMOUNT_VALUE = "CompensateAmount";
    public static final String COMMISSIONCHARGE_VALUE = "CommissionCharge";
    public static final String ELSEAMOUNT_VALUE = "ElseAmount";

    public static final BusinessTypeNameEnum PURCHASEAMOUNT = new BusinessTypeNameEnum("PURCHASEAMOUNT", PURCHASEAMOUNT_VALUE);
    public static final BusinessTypeNameEnum FISRTAMOUNT = new BusinessTypeNameEnum("FISRTAMOUNT", FISRTAMOUNT_VALUE);
    public static final BusinessTypeNameEnum HOUSEAMOUNT = new BusinessTypeNameEnum("HOUSEAMOUNT", HOUSEAMOUNT_VALUE);
    public static final BusinessTypeNameEnum LOANAMOUNT = new BusinessTypeNameEnum("LOANAMOUNT", LOANAMOUNT_VALUE);
    public static final BusinessTypeNameEnum ACCFUNDAMOUNT = new BusinessTypeNameEnum("ACCFUNDAMOUNT", ACCFUNDAMOUNT_VALUE);
    public static final BusinessTypeNameEnum REPLACEFEE = new BusinessTypeNameEnum("REPLACEFEE", REPLACEFEE_VALUE);
    public static final BusinessTypeNameEnum PRELIMINARY = new BusinessTypeNameEnum("PRELIMINARY", PRELIMINARY_VALUE);
    public static final BusinessTypeNameEnum PURCHASE = new BusinessTypeNameEnum("PURCHASE", PURCHASE_VALUE);
    public static final BusinessTypeNameEnum SIGN = new BusinessTypeNameEnum("SIGN", SIGN_VALUE);
    public static final BusinessTypeNameEnum MORTGAGE = new BusinessTypeNameEnum("MORTGAGE", MORTGAGE_VALUE);
    public static final BusinessTypeNameEnum ACCFUND = new BusinessTypeNameEnum("ACCFUND", ACCFUND_VALUE);
    public static final BusinessTypeNameEnum INTEREST = new BusinessTypeNameEnum("INTEREST", INTEREST_VALUE);
    public static final BusinessTypeNameEnum JOIN = new BusinessTypeNameEnum("JOIN", JOIN_VALUE);
    public static final BusinessTypeNameEnum AREACOMPENSATE = new BusinessTypeNameEnum("AREACOMPENSATE", AREACOMPENSATE_VALUE);
    public static final BusinessTypeNameEnum SINCER = new BusinessTypeNameEnum("SINCER", SINCER_VALUE);
    public static final BusinessTypeNameEnum PREMONEY = new BusinessTypeNameEnum("PREMONEY", PREMONEY_VALUE);
    public static final BusinessTypeNameEnum PRECONCERTMONEY = new BusinessTypeNameEnum("PRECONCERTMONEY", PRECONCERTMONEY_VALUE);
    public static final BusinessTypeNameEnum REFUNDMENT = new BusinessTypeNameEnum("REFUNDMENT", REFUNDMENT_VALUE);
    public static final BusinessTypeNameEnum FITMENTAMOUNT = new BusinessTypeNameEnum("FITMENTAMOUNT", FITMENTAMOUNT_VALUE);
    public static final BusinessTypeNameEnum LATEFEE = new BusinessTypeNameEnum("LATEFEE", LATEFEE_VALUE);
    public static final BusinessTypeNameEnum COMPENSATEAMOUNT = new BusinessTypeNameEnum("COMPENSATEAMOUNT", COMPENSATEAMOUNT_VALUE);
    public static final BusinessTypeNameEnum COMMISSIONCHARGE = new BusinessTypeNameEnum("COMMISSIONCHARGE", COMMISSIONCHARGE_VALUE);
    public static final BusinessTypeNameEnum ELSEAMOUNT = new BusinessTypeNameEnum("ELSEAMOUNT", ELSEAMOUNT_VALUE);

    /**
     * construct function
     * @param String businessTypeNameEnum
     */
    private BusinessTypeNameEnum(String name, String businessTypeNameEnum)
    {
        super(name, businessTypeNameEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static BusinessTypeNameEnum getEnum(String businessTypeNameEnum)
    {
        return (BusinessTypeNameEnum)getEnum(BusinessTypeNameEnum.class, businessTypeNameEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(BusinessTypeNameEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(BusinessTypeNameEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(BusinessTypeNameEnum.class);
    }
}