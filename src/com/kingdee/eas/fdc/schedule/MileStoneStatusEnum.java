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
public class MileStoneStatusEnum extends StringEnum
{
    public static final String START_VALUE = "0start";
    public static final String END_VALUE = "1end";

    public static final MileStoneStatusEnum start = new MileStoneStatusEnum("start", START_VALUE);
    public static final MileStoneStatusEnum end = new MileStoneStatusEnum("end", END_VALUE);

    /**
     * construct function
     * @param String mileStoneStatusEnum
     */
    private MileStoneStatusEnum(String name, String mileStoneStatusEnum)
    {
        super(name, mileStoneStatusEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static MileStoneStatusEnum getEnum(String mileStoneStatusEnum)
    {
        return (MileStoneStatusEnum)getEnum(MileStoneStatusEnum.class, mileStoneStatusEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(MileStoneStatusEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(MileStoneStatusEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(MileStoneStatusEnum.class);
    }
}