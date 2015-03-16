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
public class PlanisphereTypeEnum extends StringEnum
{
    public static final String PICSELLPROJECT_VALUE = "PicSellProject";
    public static final String PICBUILDDIST_VALUE = "PicBuildDist";
    public static final String PICBUILDPLANE_VALUE = "PicBuildPlane";

    public static final PlanisphereTypeEnum PicSellProject = new PlanisphereTypeEnum("PicSellProject", PICSELLPROJECT_VALUE);
    public static final PlanisphereTypeEnum PicBuildDist = new PlanisphereTypeEnum("PicBuildDist", PICBUILDDIST_VALUE);
    public static final PlanisphereTypeEnum PicBuildPlane = new PlanisphereTypeEnum("PicBuildPlane", PICBUILDPLANE_VALUE);

    /**
     * construct function
     * @param String planisphereTypeEnum
     */
    private PlanisphereTypeEnum(String name, String planisphereTypeEnum)
    {
        super(name, planisphereTypeEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static PlanisphereTypeEnum getEnum(String planisphereTypeEnum)
    {
        return (PlanisphereTypeEnum)getEnum(PlanisphereTypeEnum.class, planisphereTypeEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(PlanisphereTypeEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(PlanisphereTypeEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(PlanisphereTypeEnum.class);
    }
}