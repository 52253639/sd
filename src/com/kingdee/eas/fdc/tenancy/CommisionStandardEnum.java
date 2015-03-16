/**
 * output package name
 */
package com.kingdee.eas.fdc.tenancy;

import java.util.Map;
import java.util.List;
import java.util.Iterator;
import com.kingdee.util.enums.StringEnum;

/**
 * output class name
 */
public class CommisionStandardEnum extends StringEnum
{
    public static final String PERCENTUM_VALUE = "Percentum";
    public static final String APTOTICAMOUNT_VALUE = "AptoticAmount";

    public static final CommisionStandardEnum Percentum = new CommisionStandardEnum("Percentum", PERCENTUM_VALUE);
    public static final CommisionStandardEnum AptoticAmount = new CommisionStandardEnum("AptoticAmount", APTOTICAMOUNT_VALUE);

    /**
     * construct function
     * @param String commisionStandardEnum
     */
    private CommisionStandardEnum(String name, String commisionStandardEnum)
    {
        super(name, commisionStandardEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static CommisionStandardEnum getEnum(String commisionStandardEnum)
    {
        return (CommisionStandardEnum)getEnum(CommisionStandardEnum.class, commisionStandardEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(CommisionStandardEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(CommisionStandardEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(CommisionStandardEnum.class);
    }
}