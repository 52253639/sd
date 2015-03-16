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
public class RoomPropertyTypeEnum extends StringEnum
{
    public static final String PROJECTTYPE_VALUE = "1ProjectType";
    public static final String BUILDINGUNITTYPE_VALUE = "2BuildingUnitType";
    public static final String ROOMTYPE_VALUE = "4RoomType";

    public static final RoomPropertyTypeEnum PROJECTTYPE = new RoomPropertyTypeEnum("PROJECTTYPE", PROJECTTYPE_VALUE);
    public static final RoomPropertyTypeEnum BUILDINGUNITTYPE = new RoomPropertyTypeEnum("BUILDINGUNITTYPE", BUILDINGUNITTYPE_VALUE);
    public static final RoomPropertyTypeEnum ROOMTYPE = new RoomPropertyTypeEnum("ROOMTYPE", ROOMTYPE_VALUE);

    /**
     * construct function
     * @param String roomPropertyTypeEnum
     */
    private RoomPropertyTypeEnum(String name, String roomPropertyTypeEnum)
    {
        super(name, roomPropertyTypeEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static RoomPropertyTypeEnum getEnum(String roomPropertyTypeEnum)
    {
        return (RoomPropertyTypeEnum)getEnum(RoomPropertyTypeEnum.class, roomPropertyTypeEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(RoomPropertyTypeEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(RoomPropertyTypeEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(RoomPropertyTypeEnum.class);
    }
}