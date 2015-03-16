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
public class PeriodEnum extends StringEnum
{
    public static final String INNERSEVEN_VALUE = "10";
    public static final String SEVENTOFOURTEEN_VALUE = "20";
    public static final String FIFTEENTOTHIRTY_VALUE = "30";
    public static final String THIRTYONETOSIXTY_VALUE = "40";
    public static final String SIXTYMORE_VALUE = "50";

    public static final PeriodEnum InnerSeven = new PeriodEnum("InnerSeven", INNERSEVEN_VALUE);
    public static final PeriodEnum SevenToFourteen = new PeriodEnum("SevenToFourteen", SEVENTOFOURTEEN_VALUE);
    public static final PeriodEnum FifteenToThirty = new PeriodEnum("FifteenToThirty", FIFTEENTOTHIRTY_VALUE);
    public static final PeriodEnum thirtyoneToSixty = new PeriodEnum("thirtyoneToSixty", THIRTYONETOSIXTY_VALUE);
    public static final PeriodEnum SixtyMore = new PeriodEnum("SixtyMore", SIXTYMORE_VALUE);

    /**
     * construct function
     * @param String periodEnum
     */
    private PeriodEnum(String name, String periodEnum)
    {
        super(name, periodEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static PeriodEnum getEnum(String periodEnum)
    {
        return (PeriodEnum)getEnum(PeriodEnum.class, periodEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(PeriodEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(PeriodEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(PeriodEnum.class);
    }
}