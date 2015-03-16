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
public class MoreRoomsTypeEnum extends StringEnum
{
    public static final String ROOMRENTSETTING_VALUE = "roomRentSetting";
    public static final String STANDARDRENTSETTING_VALUE = "standardRentSetting";

    public static final MoreRoomsTypeEnum RoomRentSetting = new MoreRoomsTypeEnum("RoomRentSetting", ROOMRENTSETTING_VALUE);
    public static final MoreRoomsTypeEnum StandardRentSetting = new MoreRoomsTypeEnum("StandardRentSetting", STANDARDRENTSETTING_VALUE);

    /**
     * construct function
     * @param String moreRoomsTypeEnum
     */
    private MoreRoomsTypeEnum(String name, String moreRoomsTypeEnum)
    {
        super(name, moreRoomsTypeEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static MoreRoomsTypeEnum getEnum(String moreRoomsTypeEnum)
    {
        return (MoreRoomsTypeEnum)getEnum(MoreRoomsTypeEnum.class, moreRoomsTypeEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(MoreRoomsTypeEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(MoreRoomsTypeEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(MoreRoomsTypeEnum.class);
    }
}