/**
 * output package name
 */
package com.kingdee.eas.fdc.market;

import java.util.Map;
import java.util.List;
import java.util.Iterator;
import com.kingdee.util.enums.StringEnum;

/**
 * output class name
 */
public class region extends StringEnum
{
    public static final String FIRSTSEASON_VALUE = "1";
    public static final String SECONDSEASON_VALUE = "2";
    public static final String THIRDSEASON_VALUE = "3";
    public static final String FOURSEASON_VALUE = "4";
    public static final String UPYEAR_VALUE = "5";
    public static final String DOWNYEAR_VALUE = "6";

    public static final region firstSeason = new region("firstSeason", FIRSTSEASON_VALUE);
    public static final region secondSeason = new region("secondSeason", SECONDSEASON_VALUE);
    public static final region thirdSeason = new region("thirdSeason", THIRDSEASON_VALUE);
    public static final region fourSeason = new region("fourSeason", FOURSEASON_VALUE);
    public static final region upYear = new region("upYear", UPYEAR_VALUE);
    public static final region downYear = new region("downYear", DOWNYEAR_VALUE);

    /**
     * construct function
     * @param String region
     */
    private region(String name, String region)
    {
        super(name, region);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static region getEnum(String region)
    {
        return (region)getEnum(region.class, region);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(region.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(region.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(region.class);
    }
}