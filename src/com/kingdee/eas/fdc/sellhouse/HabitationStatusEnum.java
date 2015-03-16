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
public class HabitationStatusEnum extends StringEnum
{
    public static final String OWNER_VALUE = "Owner";
    public static final String TENANT_VALUE = "Tenant";

    public static final HabitationStatusEnum Owner = new HabitationStatusEnum("Owner", OWNER_VALUE);
    public static final HabitationStatusEnum Tenant = new HabitationStatusEnum("Tenant", TENANT_VALUE);

    /**
     * construct function
     * @param String habitationStatusEnum
     */
    private HabitationStatusEnum(String name, String habitationStatusEnum)
    {
        super(name, habitationStatusEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static HabitationStatusEnum getEnum(String habitationStatusEnum)
    {
        return (HabitationStatusEnum)getEnum(HabitationStatusEnum.class, habitationStatusEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(HabitationStatusEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(HabitationStatusEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(HabitationStatusEnum.class);
    }
}