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
public class GatheringObjectEnum extends StringEnum
{
    public static final String ROOM_VALUE = "room";
    public static final String EQUIPMENT_VALUE = "equipment";
    public static final String ACCESSORIALRESOURCE_VALUE = "accessorialResource";

    public static final GatheringObjectEnum room = new GatheringObjectEnum("room", ROOM_VALUE);
    public static final GatheringObjectEnum equipment = new GatheringObjectEnum("equipment", EQUIPMENT_VALUE);
    public static final GatheringObjectEnum accessorialResource = new GatheringObjectEnum("accessorialResource", ACCESSORIALRESOURCE_VALUE);

    /**
     * construct function
     * @param String gatheringObjectEnum
     */
    private GatheringObjectEnum(String name, String gatheringObjectEnum)
    {
        super(name, gatheringObjectEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static GatheringObjectEnum getEnum(String gatheringObjectEnum)
    {
        return (GatheringObjectEnum)getEnum(GatheringObjectEnum.class, gatheringObjectEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(GatheringObjectEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(GatheringObjectEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(GatheringObjectEnum.class);
    }
}