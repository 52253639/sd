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
public class BuildingStructureEnum extends StringEnum
{
    public static final String KUANGJIA_VALUE = "KuangJia";
    public static final String JIANLIQIANG_VALUE = "JianLiQiang";

    public static final BuildingStructureEnum KuangJia = new BuildingStructureEnum("KuangJia", KUANGJIA_VALUE);
    public static final BuildingStructureEnum JianLiQiang = new BuildingStructureEnum("JianLiQiang", JIANLIQIANG_VALUE);

    /**
     * construct function
     * @param String buildingStructureEnum
     */
    private BuildingStructureEnum(String name, String buildingStructureEnum)
    {
        super(name, buildingStructureEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static BuildingStructureEnum getEnum(String buildingStructureEnum)
    {
        return (BuildingStructureEnum)getEnum(BuildingStructureEnum.class, buildingStructureEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(BuildingStructureEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(BuildingStructureEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(BuildingStructureEnum.class);
    }
}