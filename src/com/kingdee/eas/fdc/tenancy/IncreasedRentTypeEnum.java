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
public class IncreasedRentTypeEnum extends StringEnum
{
    public static final String PERCENT_VALUE = "Percent";
    public static final String TOTALRENT_VALUE = "totalRent";
    public static final String RENTPRICE_VALUE = "rentPrice";
    public static final String HANDWORK_VALUE = "Handwork";

    public static final IncreasedRentTypeEnum Percent = new IncreasedRentTypeEnum("Percent", PERCENT_VALUE);
    public static final IncreasedRentTypeEnum totalRent = new IncreasedRentTypeEnum("totalRent", TOTALRENT_VALUE);
    public static final IncreasedRentTypeEnum rentPrice = new IncreasedRentTypeEnum("rentPrice", RENTPRICE_VALUE);
    public static final IncreasedRentTypeEnum Handwork = new IncreasedRentTypeEnum("Handwork", HANDWORK_VALUE);

    /**
     * construct function
     * @param String increasedRentTypeEnum
     */
    private IncreasedRentTypeEnum(String name, String increasedRentTypeEnum)
    {
        super(name, increasedRentTypeEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static IncreasedRentTypeEnum getEnum(String increasedRentTypeEnum)
    {
        return (IncreasedRentTypeEnum)getEnum(IncreasedRentTypeEnum.class, increasedRentTypeEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(IncreasedRentTypeEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(IncreasedRentTypeEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(IncreasedRentTypeEnum.class);
    }
}