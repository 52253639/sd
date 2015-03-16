/**
 * output package name
 */
package com.kingdee.eas.fdc.basecrm;

import java.util.Map;
import java.util.List;
import java.util.Iterator;
import com.kingdee.util.enums.StringEnum;

/**
 * output class name
 */
public class MaritalStatusEnum extends StringEnum
{
    public static final String UNMARRIED_VALUE = "SINGLE";
    public static final String MARRIED_VALUE = "MARRIED";
    public static final String DIVORCE_VALUE = "DIVORCE";
    public static final String WIDOWED_VALUE = "WIDOWED";

    public static final MaritalStatusEnum UNMARRIED = new MaritalStatusEnum("UNMARRIED", UNMARRIED_VALUE);
    public static final MaritalStatusEnum MARRIED = new MaritalStatusEnum("MARRIED", MARRIED_VALUE);
    public static final MaritalStatusEnum DIVORCE = new MaritalStatusEnum("DIVORCE", DIVORCE_VALUE);
    public static final MaritalStatusEnum WIDOWED = new MaritalStatusEnum("WIDOWED", WIDOWED_VALUE);

    /**
     * construct function
     * @param String maritalStatusEnum
     */
    private MaritalStatusEnum(String name, String maritalStatusEnum)
    {
        super(name, maritalStatusEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static MaritalStatusEnum getEnum(String maritalStatusEnum)
    {
        return (MaritalStatusEnum)getEnum(MaritalStatusEnum.class, maritalStatusEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(MaritalStatusEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(MaritalStatusEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(MaritalStatusEnum.class);
    }
}