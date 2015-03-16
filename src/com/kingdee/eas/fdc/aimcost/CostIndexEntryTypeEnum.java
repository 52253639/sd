/**
 * output package name
 */
package com.kingdee.eas.fdc.aimcost;

import java.util.Map;
import java.util.List;
import java.util.Iterator;
import com.kingdee.util.enums.StringEnum;

/**
 * output class name
 */
public class CostIndexEntryTypeEnum extends StringEnum
{
    public static final String PROJECT_VALUE = "PROJECT";
    public static final String MATERIAL_VALUE = "MATERIAL";

    public static final CostIndexEntryTypeEnum PROJECT = new CostIndexEntryTypeEnum("PROJECT", PROJECT_VALUE);
    public static final CostIndexEntryTypeEnum MATERIAL = new CostIndexEntryTypeEnum("MATERIAL", MATERIAL_VALUE);

    /**
     * construct function
     * @param String costIndexEntryTypeEnum
     */
    private CostIndexEntryTypeEnum(String name, String costIndexEntryTypeEnum)
    {
        super(name, costIndexEntryTypeEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static CostIndexEntryTypeEnum getEnum(String costIndexEntryTypeEnum)
    {
        return (CostIndexEntryTypeEnum)getEnum(CostIndexEntryTypeEnum.class, costIndexEntryTypeEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(CostIndexEntryTypeEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(CostIndexEntryTypeEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(CostIndexEntryTypeEnum.class);
    }
}