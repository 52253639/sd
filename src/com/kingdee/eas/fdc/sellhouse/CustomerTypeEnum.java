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
public class CustomerTypeEnum extends StringEnum
{
    public static final String PERSONALCUSTOMER_VALUE = "1PersonalCustomer";
    public static final String ENTERPRICECUSTOMER_VALUE = "2EnterpriceCustomer";

    public static final CustomerTypeEnum PersonalCustomer = new CustomerTypeEnum("PersonalCustomer", PERSONALCUSTOMER_VALUE);
    public static final CustomerTypeEnum EnterpriceCustomer = new CustomerTypeEnum("EnterpriceCustomer", ENTERPRICECUSTOMER_VALUE);

    /**
     * construct function
     * @param String customerTypeEnum
     */
    private CustomerTypeEnum(String name, String customerTypeEnum)
    {
        super(name, customerTypeEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static CustomerTypeEnum getEnum(String customerTypeEnum)
    {
        return (CustomerTypeEnum)getEnum(CustomerTypeEnum.class, customerTypeEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(CustomerTypeEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(CustomerTypeEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(CustomerTypeEnum.class);
    }
}