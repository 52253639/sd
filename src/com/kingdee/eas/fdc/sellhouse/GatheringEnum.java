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
public class GatheringEnum extends StringEnum
{
    public static final String SINPURCHASE_VALUE = "SinPurchase";
    public static final String SALEROOM_VALUE = "SaleRoom";
    public static final String OBLIGATEDROOM_VALUE = "ObligatedRoom";
    public static final String OBLIGATEDATTACH_VALUE = "ObligatedAttach";
    public static final String TENROOM_VALUE = "TenRoom";
    public static final String TENATTACH_VALUE = "TenAttach";
    public static final String WUYEROOMFEE_VALUE = "WuYeRoomFee";
    public static final String CUSTOMERREV_VALUE = "CustomerRev";

    public static final GatheringEnum SinPurchase = new GatheringEnum("SinPurchase", SINPURCHASE_VALUE);
    public static final GatheringEnum SaleRoom = new GatheringEnum("SaleRoom", SALEROOM_VALUE);
    public static final GatheringEnum ObligatedRoom = new GatheringEnum("ObligatedRoom", OBLIGATEDROOM_VALUE);
    public static final GatheringEnum ObligatedAttach = new GatheringEnum("ObligatedAttach", OBLIGATEDATTACH_VALUE);
    public static final GatheringEnum TenRoom = new GatheringEnum("TenRoom", TENROOM_VALUE);
    public static final GatheringEnum TenAttach = new GatheringEnum("TenAttach", TENATTACH_VALUE);
    public static final GatheringEnum WuYeRoomFee = new GatheringEnum("WuYeRoomFee", WUYEROOMFEE_VALUE);
    public static final GatheringEnum CustomerRev = new GatheringEnum("CustomerRev", CUSTOMERREV_VALUE);

    /**
     * construct function
     * @param String gatheringEnum
     */
    private GatheringEnum(String name, String gatheringEnum)
    {
        super(name, gatheringEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static GatheringEnum getEnum(String gatheringEnum)
    {
        return (GatheringEnum)getEnum(GatheringEnum.class, gatheringEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(GatheringEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(GatheringEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(GatheringEnum.class);
    }
}