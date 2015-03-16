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
public class LoanNotarizeEnum extends StringEnum
{
    public static final String NOTNOTARIZE_VALUE = "1NotNotarize";
    public static final String NOTARIZED_VALUE = "2Notarized";

    public static final LoanNotarizeEnum notNotarize = new LoanNotarizeEnum("notNotarize", NOTNOTARIZE_VALUE);
    public static final LoanNotarizeEnum notarized = new LoanNotarizeEnum("notarized", NOTARIZED_VALUE);

    /**
     * construct function
     * @param String loanNotarizeEnum
     */
    private LoanNotarizeEnum(String name, String loanNotarizeEnum)
    {
        super(name, loanNotarizeEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static LoanNotarizeEnum getEnum(String loanNotarizeEnum)
    {
        return (LoanNotarizeEnum)getEnum(LoanNotarizeEnum.class, loanNotarizeEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(LoanNotarizeEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(LoanNotarizeEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(LoanNotarizeEnum.class);
    }
}