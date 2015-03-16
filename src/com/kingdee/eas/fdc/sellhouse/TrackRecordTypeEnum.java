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
public class TrackRecordTypeEnum extends StringEnum
{
    public static final String CLEW_VALUE = "TR1";
    public static final String LATENCY_VALUE = "TR2";
    public static final String INTENCY_VALUE = "TR3";

    public static final TrackRecordTypeEnum Clew = new TrackRecordTypeEnum("Clew", CLEW_VALUE);
    public static final TrackRecordTypeEnum Latency = new TrackRecordTypeEnum("Latency", LATENCY_VALUE);
    public static final TrackRecordTypeEnum Intency = new TrackRecordTypeEnum("Intency", INTENCY_VALUE);

    /**
     * construct function
     * @param String trackRecordTypeEnum
     */
    private TrackRecordTypeEnum(String name, String trackRecordTypeEnum)
    {
        super(name, trackRecordTypeEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static TrackRecordTypeEnum getEnum(String trackRecordTypeEnum)
    {
        return (TrackRecordTypeEnum)getEnum(TrackRecordTypeEnum.class, trackRecordTypeEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(TrackRecordTypeEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(TrackRecordTypeEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(TrackRecordTypeEnum.class);
    }
}