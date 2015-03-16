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
public class EffectImageEnum extends StringEnum
{
    public static final String PIC_SELLPROJECT_VALUE = "1PIC_SELLPROJECT";
    public static final String PIC_BUILDING_VALUE = "2PIC_BUILDING";
    public static final String PIC_BUILDINGFLOOR_VALUE = "3PIC_BUILDINGFLOOR";

    public static final EffectImageEnum PIC_SELLPROJECT = new EffectImageEnum("PIC_SELLPROJECT", PIC_SELLPROJECT_VALUE);
    public static final EffectImageEnum PIC_BUILDING = new EffectImageEnum("PIC_BUILDING", PIC_BUILDING_VALUE);
    public static final EffectImageEnum PIC_BUILDINGFLOOR = new EffectImageEnum("PIC_BUILDINGFLOOR", PIC_BUILDINGFLOOR_VALUE);

    /**
     * construct function
     * @param String effectImageEnum
     */
    private EffectImageEnum(String name, String effectImageEnum)
    {
        super(name, effectImageEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static EffectImageEnum getEnum(String effectImageEnum)
    {
        return (EffectImageEnum)getEnum(EffectImageEnum.class, effectImageEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(EffectImageEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(EffectImageEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(EffectImageEnum.class);
    }
}