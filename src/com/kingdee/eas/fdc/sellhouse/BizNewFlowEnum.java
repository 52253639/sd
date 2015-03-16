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
public class BizNewFlowEnum extends StringEnum
{
    public static final String PREREGISTER_VALUE = "PreRegister";
    public static final String PURCHASE_VALUE = "Purchase";
    public static final String SIGN_VALUE = "Sign";
    public static final String LOAN_VALUE = "loan";
    public static final String ACCFUND_VALUE = "AccFund";
    public static final String PROPERTY_VALUE = "Property";
    public static final String JOIN_VALUE = "join";
    public static final String AREACOMPENSAGTE_VALUE = "AreaCompensagte";

    public static final BizNewFlowEnum PREREGISTER = new BizNewFlowEnum("PREREGISTER", PREREGISTER_VALUE);
    public static final BizNewFlowEnum PURCHASE = new BizNewFlowEnum("PURCHASE", PURCHASE_VALUE);
    public static final BizNewFlowEnum SIGN = new BizNewFlowEnum("SIGN", SIGN_VALUE);
    public static final BizNewFlowEnum LOAN = new BizNewFlowEnum("LOAN", LOAN_VALUE);
    public static final BizNewFlowEnum ACCFUND = new BizNewFlowEnum("ACCFUND", ACCFUND_VALUE);
    public static final BizNewFlowEnum PROPERTY = new BizNewFlowEnum("PROPERTY", PROPERTY_VALUE);
    public static final BizNewFlowEnum JOIN = new BizNewFlowEnum("JOIN", JOIN_VALUE);
    public static final BizNewFlowEnum AREACOMPENSAGTE = new BizNewFlowEnum("AREACOMPENSAGTE", AREACOMPENSAGTE_VALUE);

    /**
     * construct function
     * @param String bizNewFlowEnum
     */
    private BizNewFlowEnum(String name, String bizNewFlowEnum)
    {
        super(name, bizNewFlowEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static BizNewFlowEnum getEnum(String bizNewFlowEnum)
    {
        return (BizNewFlowEnum)getEnum(BizNewFlowEnum.class, bizNewFlowEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(BizNewFlowEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(BizNewFlowEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(BizNewFlowEnum.class);
    }
}