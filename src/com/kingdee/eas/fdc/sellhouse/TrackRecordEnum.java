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
public class TrackRecordEnum extends StringEnum
{
    public static final String SINCERITYPUR_VALUE = "S10";
    public static final String DESTINEAPP_VALUE = "S20";
    public static final String PURCHASEAPP_VALUE = "S30";
    public static final String SIGNAPP_VALUE = "S40";
    public static final String CANCELAPP_VALUE = "S50";
    public static final String TENANCYAPP_VALUE = "T10";

    public static final TrackRecordEnum SincerityPur = new TrackRecordEnum("SincerityPur", SINCERITYPUR_VALUE);
    public static final TrackRecordEnum DestineApp = new TrackRecordEnum("DestineApp", DESTINEAPP_VALUE);
    public static final TrackRecordEnum PurchaseApp = new TrackRecordEnum("PurchaseApp", PURCHASEAPP_VALUE);
    public static final TrackRecordEnum SignApp = new TrackRecordEnum("SignApp", SIGNAPP_VALUE);
    public static final TrackRecordEnum CancelApp = new TrackRecordEnum("CancelApp", CANCELAPP_VALUE);
    public static final TrackRecordEnum TenancyApp = new TrackRecordEnum("TenancyApp", TENANCYAPP_VALUE);

    /**
     * construct function
     * @param String trackRecordEnum
     */
    private TrackRecordEnum(String name, String trackRecordEnum)
    {
        super(name, trackRecordEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static TrackRecordEnum getEnum(String trackRecordEnum)
    {
        return (TrackRecordEnum)getEnum(TrackRecordEnum.class, trackRecordEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(TrackRecordEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(TrackRecordEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(TrackRecordEnum.class);
    }
}