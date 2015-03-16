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
public class SHECustomerChangeReasonEnum extends StringEnum
{
    public static final String PUTOPR_VALUE = "02";
    public static final String PRTOPU_VALUE = "03";
    public static final String SHARE_VALUE = "04";
    public static final String ADDNEW_VALUE = "01";
    public static final String TRANSMIT_VALUE = "05";

    public static final SHECustomerChangeReasonEnum PuToPr = new SHECustomerChangeReasonEnum("PuToPr", PUTOPR_VALUE);
    public static final SHECustomerChangeReasonEnum PrToPu = new SHECustomerChangeReasonEnum("PrToPu", PRTOPU_VALUE);
    public static final SHECustomerChangeReasonEnum Share = new SHECustomerChangeReasonEnum("Share", SHARE_VALUE);
    public static final SHECustomerChangeReasonEnum AddNew = new SHECustomerChangeReasonEnum("AddNew", ADDNEW_VALUE);
    public static final SHECustomerChangeReasonEnum Transmit = new SHECustomerChangeReasonEnum("Transmit", TRANSMIT_VALUE);

    /**
     * construct function
     * @param String sHECustomerChangeReasonEnum
     */
    private SHECustomerChangeReasonEnum(String name, String sHECustomerChangeReasonEnum)
    {
        super(name, sHECustomerChangeReasonEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static SHECustomerChangeReasonEnum getEnum(String sHECustomerChangeReasonEnum)
    {
        return (SHECustomerChangeReasonEnum)getEnum(SHECustomerChangeReasonEnum.class, sHECustomerChangeReasonEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(SHECustomerChangeReasonEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(SHECustomerChangeReasonEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(SHECustomerChangeReasonEnum.class);
    }
}