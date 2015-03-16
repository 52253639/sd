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
public class EnableOrNotEnum extends StringEnum
{
    public static final String ENABLED_VALUE = "1Enabled";
    public static final String NOENABLED_VALUE = "2NoEnabled";

    public static final EnableOrNotEnum Enabled = new EnableOrNotEnum("Enabled", ENABLED_VALUE);
    public static final EnableOrNotEnum NoEnabled = new EnableOrNotEnum("NoEnabled", NOENABLED_VALUE);

    /**
     * construct function
     * @param String enableOrNotEnum
     */
    private EnableOrNotEnum(String name, String enableOrNotEnum)
    {
        super(name, enableOrNotEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static EnableOrNotEnum getEnum(String enableOrNotEnum)
    {
        return (EnableOrNotEnum)getEnum(EnableOrNotEnum.class, enableOrNotEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(EnableOrNotEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(EnableOrNotEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(EnableOrNotEnum.class);
    }
}