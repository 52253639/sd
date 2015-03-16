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
public class PaymentMethodEnum extends StringEnum
{
    public static final String INTERESTONPRINCIPAL_VALUE = "0InterestOnPrincipal";
    public static final String PRINCIPAL_VALUE = "1principal";

    public static final PaymentMethodEnum InterestOnPrincipal = new PaymentMethodEnum("InterestOnPrincipal", INTERESTONPRINCIPAL_VALUE);
    public static final PaymentMethodEnum Principal = new PaymentMethodEnum("Principal", PRINCIPAL_VALUE);

    /**
     * construct function
     * @param String paymentMethodEnum
     */
    private PaymentMethodEnum(String name, String paymentMethodEnum)
    {
        super(name, paymentMethodEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static PaymentMethodEnum getEnum(String paymentMethodEnum)
    {
        return (PaymentMethodEnum)getEnum(PaymentMethodEnum.class, paymentMethodEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(PaymentMethodEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(PaymentMethodEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(PaymentMethodEnum.class);
    }
}