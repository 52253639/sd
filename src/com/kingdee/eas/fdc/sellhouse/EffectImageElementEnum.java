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
public class EffectImageElementEnum extends StringEnum
{
    public static final String SELLPROJECT_VALUE = "1SELLPROJECT";
    public static final String BUILDING_VALUE = "2BUILDING";
    public static final String ROOM_VALUE = "3ROOM";

    public static final EffectImageElementEnum SELLPROJECT = new EffectImageElementEnum("SELLPROJECT", SELLPROJECT_VALUE);
    public static final EffectImageElementEnum BUILDING = new EffectImageElementEnum("BUILDING", BUILDING_VALUE);
    public static final EffectImageElementEnum ROOM = new EffectImageElementEnum("ROOM", ROOM_VALUE);

    /**
     * construct function
     * @param String effectImageElementEnum
     */
    private EffectImageElementEnum(String name, String effectImageElementEnum)
    {
        super(name, effectImageElementEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static EffectImageElementEnum getEnum(String effectImageElementEnum)
    {
        return (EffectImageElementEnum)getEnum(EffectImageElementEnum.class, effectImageElementEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(EffectImageElementEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(EffectImageElementEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(EffectImageElementEnum.class);
    }
}