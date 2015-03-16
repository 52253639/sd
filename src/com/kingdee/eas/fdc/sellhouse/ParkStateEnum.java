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
public class ParkStateEnum extends StringEnum
{
    public static final String KONGZHI_VALUE = "KongZhi";
    public static final String CHUSHOU_VALUE = "ChuShou";
    public static final String CHUZU_VALUE = "ChuZu";

    public static final ParkStateEnum KongZhi = new ParkStateEnum("KongZhi", KONGZHI_VALUE);
    public static final ParkStateEnum ChuShou = new ParkStateEnum("ChuShou", CHUSHOU_VALUE);
    public static final ParkStateEnum ChuZu = new ParkStateEnum("ChuZu", CHUZU_VALUE);

    /**
     * construct function
     * @param String parkStateEnum
     */
    private ParkStateEnum(String name, String parkStateEnum)
    {
        super(name, parkStateEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static ParkStateEnum getEnum(String parkStateEnum)
    {
        return (ParkStateEnum)getEnum(ParkStateEnum.class, parkStateEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(ParkStateEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(ParkStateEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(ParkStateEnum.class);
    }
}