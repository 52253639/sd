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
public class LoanTypeEnum extends StringEnum
{
    public static final String BUSINESS_VALUE = "0business";
    public static final String ACCOMMODATION_VALUE = "1accommodation";
    public static final String COMBINATION_VALUE = "2combination";

    public static final LoanTypeEnum business = new LoanTypeEnum("business", BUSINESS_VALUE);
    public static final LoanTypeEnum accommodation = new LoanTypeEnum("accommodation", ACCOMMODATION_VALUE);
    public static final LoanTypeEnum combination = new LoanTypeEnum("combination", COMBINATION_VALUE);

    /**
     * construct function
     * @param String loanTypeEnum
     */
    private LoanTypeEnum(String name, String loanTypeEnum)
    {
        super(name, loanTypeEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static LoanTypeEnum getEnum(String loanTypeEnum)
    {
        return (LoanTypeEnum)getEnum(LoanTypeEnum.class, loanTypeEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(LoanTypeEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(LoanTypeEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(LoanTypeEnum.class);
    }
}