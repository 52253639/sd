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
public class TranEffectEnum extends StringEnum
{
    public static final String EFFECT_VALUE = "Effect";
    public static final String FAILURE_VALUE = "Failure";

    public static final TranEffectEnum EFFECT = new TranEffectEnum("EFFECT", EFFECT_VALUE);
    public static final TranEffectEnum FAILURE = new TranEffectEnum("FAILURE", FAILURE_VALUE);

    /**
     * construct function
     * @param String tranEffectEnum
     */
    private TranEffectEnum(String name, String tranEffectEnum)
    {
        super(name, tranEffectEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static TranEffectEnum getEnum(String tranEffectEnum)
    {
        return (TranEffectEnum)getEnum(TranEffectEnum.class, tranEffectEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(TranEffectEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(TranEffectEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(TranEffectEnum.class);
    }
}