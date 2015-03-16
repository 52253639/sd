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
public class TrackPhaseEnum extends StringEnum
{
    public static final String POTENTIALCUSTOMER_VALUE = "1PotentialCustomer";
    public static final String INTENTCUSTOMER_VALUE = "2IntentCustomer";
    public static final String SINCERITYCUSTOMER_VALUE = "3SincerityCustomer";
    public static final String PREPURCHASECUSTOMER_VALUE = "4PrePurchaseCustomer";
    public static final String PURCHASECUSTOMER_VALUE = "5PrePurchaseCustomer";
    public static final String SIGNCUSTOMER_VALUE = "6SignCustomer";

    public static final TrackPhaseEnum PotentialCustomer = new TrackPhaseEnum("PotentialCustomer", POTENTIALCUSTOMER_VALUE);
    public static final TrackPhaseEnum IntentCustomer = new TrackPhaseEnum("IntentCustomer", INTENTCUSTOMER_VALUE);
    public static final TrackPhaseEnum SincerityCustomer = new TrackPhaseEnum("SincerityCustomer", SINCERITYCUSTOMER_VALUE);
    public static final TrackPhaseEnum PrePurchaseCustomer = new TrackPhaseEnum("PrePurchaseCustomer", PREPURCHASECUSTOMER_VALUE);
    public static final TrackPhaseEnum PurchaseCustomer = new TrackPhaseEnum("PurchaseCustomer", PURCHASECUSTOMER_VALUE);
    public static final TrackPhaseEnum SignCustomer = new TrackPhaseEnum("SignCustomer", SIGNCUSTOMER_VALUE);

    /**
     * construct function
     * @param String trackPhaseEnum
     */
    private TrackPhaseEnum(String name, String trackPhaseEnum)
    {
        super(name, trackPhaseEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static TrackPhaseEnum getEnum(String trackPhaseEnum)
    {
        return (TrackPhaseEnum)getEnum(TrackPhaseEnum.class, trackPhaseEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(TrackPhaseEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(TrackPhaseEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(TrackPhaseEnum.class);
    }
}