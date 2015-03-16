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
public class MoneyTypeEnum extends StringEnum
{
    public static final String PREMONEY_VALUE = "PreMoney";
    public static final String PRECONCERTMONEY_VALUE = "PreconcertMoney";
    public static final String EARNESTMONEY_VALUE = "PurchaseAmount";
    public static final String FISRTAMOUNT_VALUE = "FisrtAmount";
    public static final String HOUSEAMOUNT_VALUE = "HouseAmount";
    public static final String LOANAMOUNT_VALUE = "LoanAmount";
    public static final String ACCFUNDAMOUNT_VALUE = "AccFundAmount";
    public static final String COMPENSATEAMOUNT_VALUE = "CompensateAmount";
    public static final String COMMISSIONCHARGE_VALUE = "CommissionCharge";
    public static final String LATEFEE_VALUE = "LateFee";
    public static final String FITMENTAMOUNT_VALUE = "FitmentAmount";
    public static final String REFUNDMENT_VALUE = "Refundment";
    public static final String RENTAMOUNT_VALUE = "RentAmount";
    public static final String DEPOSITAMOUNT_VALUE = "DepositAmount";
    public static final String ELSEAMOUNT_VALUE = "ElseAmount";
    public static final String CARRYFORWARDAMOUNT_VALUE = "CarryForwardAmount";
    public static final String TAXFEE_VALUE = "TaxFee";
    public static final String BREACHFEE_VALUE = "BreachFee";
    public static final String TRADEFEE_VALUE = "TradeFee";
    public static final String REPLACEFEE_VALUE = "ReplaceFee";
    public static final String PERIODICITYAMOUNT_VALUE = "PeriodicityAmount";
    public static final String SINPUR_VALUE = "SinPurMoney";

    public static final MoneyTypeEnum PreMoney = new MoneyTypeEnum("PreMoney", PREMONEY_VALUE);
    public static final MoneyTypeEnum PreconcertMoney = new MoneyTypeEnum("PreconcertMoney", PRECONCERTMONEY_VALUE);
    public static final MoneyTypeEnum EarnestMoney = new MoneyTypeEnum("EarnestMoney", EARNESTMONEY_VALUE);
    public static final MoneyTypeEnum FisrtAmount = new MoneyTypeEnum("FisrtAmount", FISRTAMOUNT_VALUE);
    public static final MoneyTypeEnum HouseAmount = new MoneyTypeEnum("HouseAmount", HOUSEAMOUNT_VALUE);
    public static final MoneyTypeEnum LoanAmount = new MoneyTypeEnum("LoanAmount", LOANAMOUNT_VALUE);
    public static final MoneyTypeEnum AccFundAmount = new MoneyTypeEnum("AccFundAmount", ACCFUNDAMOUNT_VALUE);
    public static final MoneyTypeEnum CompensateAmount = new MoneyTypeEnum("CompensateAmount", COMPENSATEAMOUNT_VALUE);
    public static final MoneyTypeEnum CommissionCharge = new MoneyTypeEnum("CommissionCharge", COMMISSIONCHARGE_VALUE);
    public static final MoneyTypeEnum LateFee = new MoneyTypeEnum("LateFee", LATEFEE_VALUE);
    public static final MoneyTypeEnum FitmentAmount = new MoneyTypeEnum("FitmentAmount", FITMENTAMOUNT_VALUE);
    public static final MoneyTypeEnum Refundment = new MoneyTypeEnum("Refundment", REFUNDMENT_VALUE);
    public static final MoneyTypeEnum RentAmount = new MoneyTypeEnum("RentAmount", RENTAMOUNT_VALUE);
    public static final MoneyTypeEnum DepositAmount = new MoneyTypeEnum("DepositAmount", DEPOSITAMOUNT_VALUE);
    public static final MoneyTypeEnum ElseAmount = new MoneyTypeEnum("ElseAmount", ELSEAMOUNT_VALUE);
    public static final MoneyTypeEnum CarryForwardAmount = new MoneyTypeEnum("CarryForwardAmount", CARRYFORWARDAMOUNT_VALUE);
    public static final MoneyTypeEnum TaxFee = new MoneyTypeEnum("TaxFee", TAXFEE_VALUE);
    public static final MoneyTypeEnum BreachFee = new MoneyTypeEnum("BreachFee", BREACHFEE_VALUE);
    public static final MoneyTypeEnum TradeFee = new MoneyTypeEnum("TradeFee", TRADEFEE_VALUE);
    public static final MoneyTypeEnum ReplaceFee = new MoneyTypeEnum("ReplaceFee", REPLACEFEE_VALUE);
    public static final MoneyTypeEnum PeriodicityAmount = new MoneyTypeEnum("PeriodicityAmount", PERIODICITYAMOUNT_VALUE);
    public static final MoneyTypeEnum SinPur = new MoneyTypeEnum("SinPur", SINPUR_VALUE);

    /**
     * construct function
     * @param String moneyTypeEnum
     */
    private MoneyTypeEnum(String name, String moneyTypeEnum)
    {
        super(name, moneyTypeEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static MoneyTypeEnum getEnum(String moneyTypeEnum)
    {
        return (MoneyTypeEnum)getEnum(MoneyTypeEnum.class, moneyTypeEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(MoneyTypeEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(MoneyTypeEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(MoneyTypeEnum.class);
    }
}