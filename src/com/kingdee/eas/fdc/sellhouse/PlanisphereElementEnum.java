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
public class PlanisphereElementEnum extends StringEnum
{
    public static final String SELLPROJECT_VALUE = "sellProject";
    public static final String BUILDING_VALUE = "building";
    public static final String ROOM_VALUE = "room";
    public static final String SPACE_VALUE = "space";

    public static final PlanisphereElementEnum sellProject = new PlanisphereElementEnum("sellProject", SELLPROJECT_VALUE);
    public static final PlanisphereElementEnum building = new PlanisphereElementEnum("building", BUILDING_VALUE);
    public static final PlanisphereElementEnum room = new PlanisphereElementEnum("room", ROOM_VALUE);
    public static final PlanisphereElementEnum space = new PlanisphereElementEnum("space", SPACE_VALUE);

    /**
     * construct function
     * @param String planisphereElementEnum
     */
    private PlanisphereElementEnum(String name, String planisphereElementEnum)
    {
        super(name, planisphereElementEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static PlanisphereElementEnum getEnum(String planisphereElementEnum)
    {
        return (PlanisphereElementEnum)getEnum(PlanisphereElementEnum.class, planisphereElementEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(PlanisphereElementEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(PlanisphereElementEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(PlanisphereElementEnum.class);
    }
}