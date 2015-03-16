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
public class RentTypeEnum extends StringEnum
{
    public static final String RENTBYYEAR_VALUE = "1RentByYear";
    public static final String RENTBYQUARTER_VALUE = "2RentByQuarter";
    public static final String RENTBYMONTH_VALUE = "3RentByMonth";
    public static final String RENTBYWEEK_VALUE = "4RentByWeek";
    public static final String RENTBYDAY_VALUE = "5RentByDay";

    public static final RentTypeEnum RentByYear = new RentTypeEnum("RentByYear", RENTBYYEAR_VALUE);
    public static final RentTypeEnum RentByQuarter = new RentTypeEnum("RentByQuarter", RENTBYQUARTER_VALUE);
    public static final RentTypeEnum RentByMonth = new RentTypeEnum("RentByMonth", RENTBYMONTH_VALUE);
    public static final RentTypeEnum RentByWeek = new RentTypeEnum("RentByWeek", RENTBYWEEK_VALUE);
    public static final RentTypeEnum RentByDay = new RentTypeEnum("RentByDay", RENTBYDAY_VALUE);

    /**
     * construct function
     * @param String rentTypeEnum
     */
    private RentTypeEnum(String name, String rentTypeEnum)
    {
        super(name, rentTypeEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static RentTypeEnum getEnum(String rentTypeEnum)
    {
        return (RentTypeEnum)getEnum(RentTypeEnum.class, rentTypeEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(RentTypeEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(RentTypeEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(RentTypeEnum.class);
    }
}