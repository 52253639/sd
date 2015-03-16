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
public class TrackEventEnum extends StringEnum
{
    public static final String SINCERPURCHASE_VALUE = "SincerPurchase";
    public static final String PREPURCHASE_VALUE = "PrePurchase";
    public static final String PURCHASE_VALUE = "Purchase";
    public static final String SIGN_VALUE = "Sign";
    public static final String CHANGROOM_VALUE = "ChangRoom";
    public static final String QUITROOM_VALUE = "QuitRoom";
    public static final String CHANGNAME_VALUE = "ChangName";
    public static final String CHANGEPRICE_VALUE = "ChangePrice";

    public static final TrackEventEnum SincerPurchase = new TrackEventEnum("SincerPurchase", SINCERPURCHASE_VALUE);
    public static final TrackEventEnum PrePurchase = new TrackEventEnum("PrePurchase", PREPURCHASE_VALUE);
    public static final TrackEventEnum Purchase = new TrackEventEnum("Purchase", PURCHASE_VALUE);
    public static final TrackEventEnum Sign = new TrackEventEnum("Sign", SIGN_VALUE);
    public static final TrackEventEnum ChangRoom = new TrackEventEnum("ChangRoom", CHANGROOM_VALUE);
    public static final TrackEventEnum QuitRoom = new TrackEventEnum("QuitRoom", QUITROOM_VALUE);
    public static final TrackEventEnum ChangName = new TrackEventEnum("ChangName", CHANGNAME_VALUE);
    public static final TrackEventEnum ChangePrice = new TrackEventEnum("ChangePrice", CHANGEPRICE_VALUE);

    /**
     * construct function
     * @param String trackEventEnum
     */
    private TrackEventEnum(String name, String trackEventEnum)
    {
        super(name, trackEventEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static TrackEventEnum getEnum(String trackEventEnum)
    {
        return (TrackEventEnum)getEnum(TrackEventEnum.class, trackEventEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(TrackEventEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(TrackEventEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(TrackEventEnum.class);
    }
}