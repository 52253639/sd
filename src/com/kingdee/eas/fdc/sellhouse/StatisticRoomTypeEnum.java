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
public class StatisticRoomTypeEnum extends StringEnum
{
    public static final String TOTALSUM_VALUE = "totalSum";
    public static final String TOTALBUILDINGAREA_VALUE = "totalBuildingArea";
    public static final String TOTALROOMAREA_VALUE = "totalRoomArea";
    public static final String TOTALSTANDARDTOTALPRICE_VALUE = "totalStandardTotalPrice";
    public static final String TOTALBASETOTALPRICE_VALUE = "totalBaseTotalPrice";
    public static final String UNSELLSUM_VALUE = "unSellSum";
    public static final String UNSELLBUILDINGAREA_VALUE = "unSellBuildingArea";
    public static final String UNSELLROOMAREA_VALUE = "unSellRoomArea";
    public static final String UNSELLBASETOTALPRICE_VALUE = "unSellBaseTotalPrice";
    public static final String UNSELLSTANDARDTOTALPRICE_VALUE = "unSellStandardTotalPrice";
    public static final String SELLSUM_VALUE = "sellSum";
    public static final String SELLBUILDINGAREA_VALUE = "sellBuildingArea";
    public static final String SELLROOMAREA_VALUE = "sellRoomArea";
    public static final String COMPENSATEBUILDINGAREA_VALUE = "compensateBuildingArea";
    public static final String COMPENSATEROOMAREA_VALUE = "compensateRoomArea";
    public static final String SELLBASETOTALPRICE_VALUE = "sellBaseTotalPrice";
    public static final String SELLSTANDARDTOTALPRICE_VALUE = "sellStandardTotalPrice";
    public static final String SELLDEALTOTALPRICE_VALUE = "sellDealTotalPrice";
    public static final String SELLCONTRACTTOTALPRICE_VALUE = "sellContractTotalPrice";
    public static final String SELLCOMPENSATEPRICE_VALUE = "sellCompensatePrice";
    public static final String SELLSALETOTALPRICE_VALUE = "sellSaleTotalPrice";
    public static final String SELLGATHERING_VALUE = "sellGathering";
    public static final String ARREARAGESUM_VALUE = "arrearageSum";

    public static final StatisticRoomTypeEnum totalSum = new StatisticRoomTypeEnum("totalSum", TOTALSUM_VALUE);
    public static final StatisticRoomTypeEnum totalBuildingArea = new StatisticRoomTypeEnum("totalBuildingArea", TOTALBUILDINGAREA_VALUE);
    public static final StatisticRoomTypeEnum totalRoomArea = new StatisticRoomTypeEnum("totalRoomArea", TOTALROOMAREA_VALUE);
    public static final StatisticRoomTypeEnum totalStandardTotalPrice = new StatisticRoomTypeEnum("totalStandardTotalPrice", TOTALSTANDARDTOTALPRICE_VALUE);
    public static final StatisticRoomTypeEnum totalBaseTotalPrice = new StatisticRoomTypeEnum("totalBaseTotalPrice", TOTALBASETOTALPRICE_VALUE);
    public static final StatisticRoomTypeEnum unSellSum = new StatisticRoomTypeEnum("unSellSum", UNSELLSUM_VALUE);
    public static final StatisticRoomTypeEnum unSellBuildingArea = new StatisticRoomTypeEnum("unSellBuildingArea", UNSELLBUILDINGAREA_VALUE);
    public static final StatisticRoomTypeEnum unSellRoomArea = new StatisticRoomTypeEnum("unSellRoomArea", UNSELLROOMAREA_VALUE);
    public static final StatisticRoomTypeEnum unSellBaseTotalPrice = new StatisticRoomTypeEnum("unSellBaseTotalPrice", UNSELLBASETOTALPRICE_VALUE);
    public static final StatisticRoomTypeEnum unSellStandardTotalPrice = new StatisticRoomTypeEnum("unSellStandardTotalPrice", UNSELLSTANDARDTOTALPRICE_VALUE);
    public static final StatisticRoomTypeEnum sellSum = new StatisticRoomTypeEnum("sellSum", SELLSUM_VALUE);
    public static final StatisticRoomTypeEnum sellBuildingArea = new StatisticRoomTypeEnum("sellBuildingArea", SELLBUILDINGAREA_VALUE);
    public static final StatisticRoomTypeEnum sellRoomArea = new StatisticRoomTypeEnum("sellRoomArea", SELLROOMAREA_VALUE);
    public static final StatisticRoomTypeEnum compensateBuildingArea = new StatisticRoomTypeEnum("compensateBuildingArea", COMPENSATEBUILDINGAREA_VALUE);
    public static final StatisticRoomTypeEnum compensateRoomArea = new StatisticRoomTypeEnum("compensateRoomArea", COMPENSATEROOMAREA_VALUE);
    public static final StatisticRoomTypeEnum sellBaseTotalPrice = new StatisticRoomTypeEnum("sellBaseTotalPrice", SELLBASETOTALPRICE_VALUE);
    public static final StatisticRoomTypeEnum sellStandardTotalPrice = new StatisticRoomTypeEnum("sellStandardTotalPrice", SELLSTANDARDTOTALPRICE_VALUE);
    public static final StatisticRoomTypeEnum sellDealTotalPrice = new StatisticRoomTypeEnum("sellDealTotalPrice", SELLDEALTOTALPRICE_VALUE);
    public static final StatisticRoomTypeEnum sellContractTotalPrice = new StatisticRoomTypeEnum("sellContractTotalPrice", SELLCONTRACTTOTALPRICE_VALUE);
    public static final StatisticRoomTypeEnum sellCompensatePrice = new StatisticRoomTypeEnum("sellCompensatePrice", SELLCOMPENSATEPRICE_VALUE);
    public static final StatisticRoomTypeEnum sellSaleTotalPrice = new StatisticRoomTypeEnum("sellSaleTotalPrice", SELLSALETOTALPRICE_VALUE);
    public static final StatisticRoomTypeEnum sellGathering = new StatisticRoomTypeEnum("sellGathering", SELLGATHERING_VALUE);
    public static final StatisticRoomTypeEnum arrearageSum = new StatisticRoomTypeEnum("arrearageSum", ARREARAGESUM_VALUE);

    /**
     * construct function
     * @param String statisticRoomTypeEnum
     */
    private StatisticRoomTypeEnum(String name, String statisticRoomTypeEnum)
    {
        super(name, statisticRoomTypeEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static StatisticRoomTypeEnum getEnum(String statisticRoomTypeEnum)
    {
        return (StatisticRoomTypeEnum)getEnum(StatisticRoomTypeEnum.class, statisticRoomTypeEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(StatisticRoomTypeEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(StatisticRoomTypeEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(StatisticRoomTypeEnum.class);
    }
}