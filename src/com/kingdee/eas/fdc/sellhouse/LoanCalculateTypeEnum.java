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
public class LoanCalculateTypeEnum extends StringEnum
{
    public static final String EQUELAMOUNT_VALUE = "EquelAmount";
    public static final String DESCENDINGAMOUNT_VALUE = "DescendingAmount";

    public static final LoanCalculateTypeEnum EquelAmount = new LoanCalculateTypeEnum("EquelAmount", EQUELAMOUNT_VALUE);
    public static final LoanCalculateTypeEnum DescendingAmount = new LoanCalculateTypeEnum("DescendingAmount", DESCENDINGAMOUNT_VALUE);

    /**
     * construct function
     * @param String loanCalculateTypeEnum
     */
    private LoanCalculateTypeEnum(String name, String loanCalculateTypeEnum)
    {
        super(name, loanCalculateTypeEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static LoanCalculateTypeEnum getEnum(String loanCalculateTypeEnum)
    {
        return (LoanCalculateTypeEnum)getEnum(LoanCalculateTypeEnum.class, loanCalculateTypeEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(LoanCalculateTypeEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(LoanCalculateTypeEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(LoanCalculateTypeEnum.class);
    }
}