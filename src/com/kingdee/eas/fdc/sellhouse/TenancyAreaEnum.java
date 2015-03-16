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
public class TenancyAreaEnum extends StringEnum
{
    public static final String BUILDINGAREA_VALUE = "buildingArea";
    public static final String ROOMAREA_VALUE = "roomArea";
    public static final String ACTUALBUILDINGAREA_VALUE = "actualBuildingArea";
    public static final String ACTUALROOMAREA_VALUE = "actualRoomArea";

    public static final TenancyAreaEnum buildingArea = new TenancyAreaEnum("buildingArea", BUILDINGAREA_VALUE);
    public static final TenancyAreaEnum roomArea = new TenancyAreaEnum("roomArea", ROOMAREA_VALUE);
    public static final TenancyAreaEnum actualBuildingArea = new TenancyAreaEnum("actualBuildingArea", ACTUALBUILDINGAREA_VALUE);
    public static final TenancyAreaEnum actualRoomArea = new TenancyAreaEnum("actualRoomArea", ACTUALROOMAREA_VALUE);

    /**
     * construct function
     * @param String tenancyAreaEnum
     */
    private TenancyAreaEnum(String name, String tenancyAreaEnum)
    {
        super(name, tenancyAreaEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static TenancyAreaEnum getEnum(String tenancyAreaEnum)
    {
        return (TenancyAreaEnum)getEnum(TenancyAreaEnum.class, tenancyAreaEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(TenancyAreaEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(TenancyAreaEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(TenancyAreaEnum.class);
    }
}