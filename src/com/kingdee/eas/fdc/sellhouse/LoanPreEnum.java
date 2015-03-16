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
public class LoanPreEnum extends StringEnum
{
    public static final String TENTHOUSAND_VALUE = "tenthousand";
    public static final String THOUSAND_VALUE = "thousand";
    public static final String HUNDRED_VALUE = "hundred";
    public static final String TEN_VALUE = "ten";

    public static final LoanPreEnum TENTHOUSAND = new LoanPreEnum("TENTHOUSAND", TENTHOUSAND_VALUE);
    public static final LoanPreEnum THOUSAND = new LoanPreEnum("THOUSAND", THOUSAND_VALUE);
    public static final LoanPreEnum HUNDRED = new LoanPreEnum("HUNDRED", HUNDRED_VALUE);
    public static final LoanPreEnum TEN = new LoanPreEnum("TEN", TEN_VALUE);

    /**
     * construct function
     * @param String loanPreEnum
     */
    private LoanPreEnum(String name, String loanPreEnum)
    {
        super(name, loanPreEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static LoanPreEnum getEnum(String loanPreEnum)
    {
        return (LoanPreEnum)getEnum(LoanPreEnum.class, loanPreEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(LoanPreEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(LoanPreEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(LoanPreEnum.class);
    }
}