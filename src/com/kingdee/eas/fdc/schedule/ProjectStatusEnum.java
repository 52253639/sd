/**
 * output package name
 */
package com.kingdee.eas.fdc.schedule;

import java.util.Map;
import java.util.List;
import java.util.Iterator;
import com.kingdee.util.enums.StringEnum;

/**
 * output class name
 */
public class ProjectStatusEnum extends StringEnum
{
    public static final String SGZB_VALUE = "01";
    public static final String SG_VALUE = "02";
    public static final String FI_VALUE = "03";
    public static final String FISR_VALUE = "04";
    public static final String CL_VALUE = "05";

    public static final ProjectStatusEnum SGZB = new ProjectStatusEnum("SGZB", SGZB_VALUE);
    public static final ProjectStatusEnum SG = new ProjectStatusEnum("SG", SG_VALUE);
    public static final ProjectStatusEnum FI = new ProjectStatusEnum("FI", FI_VALUE);
    public static final ProjectStatusEnum FISR = new ProjectStatusEnum("FISR", FISR_VALUE);
    public static final ProjectStatusEnum CL = new ProjectStatusEnum("CL", CL_VALUE);

    /**
     * construct function
     * @param String projectStatusEnum
     */
    private ProjectStatusEnum(String name, String projectStatusEnum)
    {
        super(name, projectStatusEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static ProjectStatusEnum getEnum(String projectStatusEnum)
    {
        return (ProjectStatusEnum)getEnum(ProjectStatusEnum.class, projectStatusEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(ProjectStatusEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(ProjectStatusEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(ProjectStatusEnum.class);
    }
}