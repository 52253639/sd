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
public class RentCountTypeEnum extends StringEnum
{
    public static final String ACTDATECOUNT_VALUE = "ActDateCount";
    public static final String EQUATIONPERMONTH_VALUE = "EquationPerMonth";

    public static final RentCountTypeEnum ActDateCount = new RentCountTypeEnum("ActDateCount", ACTDATECOUNT_VALUE);
    public static final RentCountTypeEnum EquationPerMonth = new RentCountTypeEnum("EquationPerMonth", EQUATIONPERMONTH_VALUE);

    /**
     * construct function
     * @param String rentCountTypeEnum
     */
    private RentCountTypeEnum(String name, String rentCountTypeEnum)
    {
        super(name, rentCountTypeEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static RentCountTypeEnum getEnum(String rentCountTypeEnum)
    {
        return (RentCountTypeEnum)getEnum(RentCountTypeEnum.class, rentCountTypeEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(RentCountTypeEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(RentCountTypeEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(RentCountTypeEnum.class);
    }
}