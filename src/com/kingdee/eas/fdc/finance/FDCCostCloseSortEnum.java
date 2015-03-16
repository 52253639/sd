/**
 * output package name
 */
package com.kingdee.eas.fdc.finance;

import java.util.Map;
import java.util.List;
import java.util.Iterator;
import com.kingdee.util.enums.StringEnum;

/**
 * output class name
 */
public class FDCCostCloseSortEnum extends StringEnum
{
    public static final String COSTCLOSERPT_VALUE = "1COSTCLOSERPT";
    public static final String COSTUNCLOSERPT_VALUE = "2COSTUNCLOSERPT";

    public static final FDCCostCloseSortEnum COSTCLOSERPT = new FDCCostCloseSortEnum("COSTCLOSERPT", COSTCLOSERPT_VALUE);
    public static final FDCCostCloseSortEnum COSTUNCLOSERPT = new FDCCostCloseSortEnum("COSTUNCLOSERPT", COSTUNCLOSERPT_VALUE);

    /**
     * construct function
     * @param String fDCCostCloseSortEnum
     */
    private FDCCostCloseSortEnum(String name, String fDCCostCloseSortEnum)
    {
        super(name, fDCCostCloseSortEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static FDCCostCloseSortEnum getEnum(String fDCCostCloseSortEnum)
    {
        return (FDCCostCloseSortEnum)getEnum(FDCCostCloseSortEnum.class, fDCCostCloseSortEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(FDCCostCloseSortEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(FDCCostCloseSortEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(FDCCostCloseSortEnum.class);
    }
}