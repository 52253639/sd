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
public class AFMmmTypeEnum extends StringEnum
{
    public static final String LOANAMOUNT_VALUE = "LoanAmount";
    public static final String ACCFUNDAMOUNT_VALUE = "AccFundAmount";

    public static final AFMmmTypeEnum LoanAmount = new AFMmmTypeEnum("LoanAmount", LOANAMOUNT_VALUE);
    public static final AFMmmTypeEnum AccFundAmount = new AFMmmTypeEnum("AccFundAmount", ACCFUNDAMOUNT_VALUE);

    /**
     * construct function
     * @param String aFMmmTypeEnum
     */
    private AFMmmTypeEnum(String name, String aFMmmTypeEnum)
    {
        super(name, aFMmmTypeEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static AFMmmTypeEnum getEnum(String aFMmmTypeEnum)
    {
        return (AFMmmTypeEnum)getEnum(AFMmmTypeEnum.class, aFMmmTypeEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(AFMmmTypeEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(AFMmmTypeEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(AFMmmTypeEnum.class);
    }
}