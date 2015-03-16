/**
 * output package name
 */
package com.kingdee.eas.fdc.invite;

import java.util.Map;
import java.util.List;
import java.util.Iterator;
import com.kingdee.util.enums.IntEnum;

/**
 * output class name
 */
public class EffectivenessEnum extends IntEnum
{
    public static final int EFFECTIVE_VALUE = 1;
    public static final int NOTEFFECTIVE_VALUE = 0;

    public static final EffectivenessEnum EFFECTIVE = new EffectivenessEnum("EFFECTIVE", EFFECTIVE_VALUE);
    public static final EffectivenessEnum NOTEFFECTIVE = new EffectivenessEnum("NOTEFFECTIVE", NOTEFFECTIVE_VALUE);

    /**
     * construct function
     * @param integer effectivenessEnum
     */
    private EffectivenessEnum(String name, int effectivenessEnum)
    {
        super(name, effectivenessEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static EffectivenessEnum getEnum(String effectivenessEnum)
    {
        return (EffectivenessEnum)getEnum(EffectivenessEnum.class, effectivenessEnum);
    }

    /**
     * getEnum function
     * @param String arguments
     */
    public static EffectivenessEnum getEnum(int effectivenessEnum)
    {
        return (EffectivenessEnum)getEnum(EffectivenessEnum.class, effectivenessEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(EffectivenessEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(EffectivenessEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(EffectivenessEnum.class);
    }
}