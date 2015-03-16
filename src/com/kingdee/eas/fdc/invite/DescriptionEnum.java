/**
 * output package name
 */
package com.kingdee.eas.fdc.invite;

import java.util.Map;
import java.util.List;
import java.util.Iterator;
import com.kingdee.util.enums.StringEnum;

/**
 * output class name
 */
public class DescriptionEnum extends StringEnum
{
    public static final String SYSTEM_VALUE = "1System";
    public static final String PERSONAL_VALUE = "2Personal";
    public static final String TOTALPRICE_VALUE = "3TotalPrice";
    public static final String TOTALPRICESUM_VALUE = "4TotalPriceSum";
    public static final String PROJECTAMT_VALUE = "5ProjectAmt";
    public static final String PROJECTAMTSUM_VALUE = "6ProjectAmtSum";
    public static final String AMOUNT_VALUE = "7Amount";
    public static final String AMOUNTSUM_VALUE = "8AmountSum";

    public static final DescriptionEnum System = new DescriptionEnum("System", SYSTEM_VALUE);
    public static final DescriptionEnum Personal = new DescriptionEnum("Personal", PERSONAL_VALUE);
    public static final DescriptionEnum TotalPrice = new DescriptionEnum("TotalPrice", TOTALPRICE_VALUE);
    public static final DescriptionEnum TotalPriceSum = new DescriptionEnum("TotalPriceSum", TOTALPRICESUM_VALUE);
    public static final DescriptionEnum ProjectAmt = new DescriptionEnum("ProjectAmt", PROJECTAMT_VALUE);
    public static final DescriptionEnum ProjectAmtSum = new DescriptionEnum("ProjectAmtSum", PROJECTAMTSUM_VALUE);
    public static final DescriptionEnum Amount = new DescriptionEnum("Amount", AMOUNT_VALUE);
    public static final DescriptionEnum AmountSum = new DescriptionEnum("AmountSum", AMOUNTSUM_VALUE);

    /**
     * construct function
     * @param String descriptionEnum
     */
    private DescriptionEnum(String name, String descriptionEnum)
    {
        super(name, descriptionEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static DescriptionEnum getEnum(String descriptionEnum)
    {
        return (DescriptionEnum)getEnum(DescriptionEnum.class, descriptionEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(DescriptionEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(DescriptionEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(DescriptionEnum.class);
    }
}