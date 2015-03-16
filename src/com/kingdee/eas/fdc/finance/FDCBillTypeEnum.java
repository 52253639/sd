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
public class FDCBillTypeEnum extends StringEnum
{
    public static final String CONTRACT_VALUE = "1CONTRACT";
    public static final String CHANGE_VALUE = "2CHANGE";
    public static final String SETTLEMENT_VALUE = "3SETTLEMENT";
    public static final String PAYMENT_VALUE = "4PAYMENT";
    public static final String CONTRACTSPLIT_VALUE = "11CONTRACTSPLIT";
    public static final String CHANGESPLIT_VALUE = "12CHANGESPLIT";
    public static final String SETTLEMENTSPLIT_VALUE = "13SETTLEMENTSPLIT";
    public static final String PAYMENTSPLIT_VALUE = "14PAYMENTSPLIT";

    public static final FDCBillTypeEnum CONTRACT = new FDCBillTypeEnum("CONTRACT", CONTRACT_VALUE);
    public static final FDCBillTypeEnum CHANGE = new FDCBillTypeEnum("CHANGE", CHANGE_VALUE);
    public static final FDCBillTypeEnum SETTLEMENT = new FDCBillTypeEnum("SETTLEMENT", SETTLEMENT_VALUE);
    public static final FDCBillTypeEnum PAYMENT = new FDCBillTypeEnum("PAYMENT", PAYMENT_VALUE);
    public static final FDCBillTypeEnum CONTRACTSPLIT = new FDCBillTypeEnum("CONTRACTSPLIT", CONTRACTSPLIT_VALUE);
    public static final FDCBillTypeEnum CHANGESPLIT = new FDCBillTypeEnum("CHANGESPLIT", CHANGESPLIT_VALUE);
    public static final FDCBillTypeEnum SETTLEMENTSPLIT = new FDCBillTypeEnum("SETTLEMENTSPLIT", SETTLEMENTSPLIT_VALUE);
    public static final FDCBillTypeEnum PAYMENTSPLIT = new FDCBillTypeEnum("PAYMENTSPLIT", PAYMENTSPLIT_VALUE);

    /**
     * construct function
     * @param String fDCBillTypeEnum
     */
    private FDCBillTypeEnum(String name, String fDCBillTypeEnum)
    {
        super(name, fDCBillTypeEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static FDCBillTypeEnum getEnum(String fDCBillTypeEnum)
    {
        return (FDCBillTypeEnum)getEnum(FDCBillTypeEnum.class, fDCBillTypeEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(FDCBillTypeEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(FDCBillTypeEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(FDCBillTypeEnum.class);
    }
}