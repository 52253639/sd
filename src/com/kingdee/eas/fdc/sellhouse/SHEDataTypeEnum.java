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
public class SHEDataTypeEnum extends StringEnum
{
    public static final String TOTALCOUNT_VALUE = "TotalCount";
    public static final String TOTALBUILDINGAREA_VALUE = "TotalBuildingArea";
    public static final String TOTALROOMAREA_VALUE = "TotalRoomArea";
    public static final String TOTALSTANDARDAMOUNT_VALUE = "TotalStandardAmount";
    public static final String NOSELLCOUNT_VALUE = "NoSellCount";
    public static final String NOSELLBUILDINGAREA_VALUE = "NoSellBuildingArea";
    public static final String NOSELLROOMAREA_VALUE = "NoSellRoomArea";
    public static final String NOSELLSTANDARDAMOUNT_VALUE = "NoSellStandardAmount";
    public static final String SELLCOUNT_VALUE = "SellCount";
    public static final String SELLBUILDINGAREA_VALUE = "SellBuildingArea";
    public static final String SELLROOMAREA_VALUE = "SellRoomArea";
    public static final String SELLSTANDARDAMOUNT_VALUE = "SellStandardAmount";
    public static final String SELLDEALAMOUNT_VALUE = "SellDealAmount";
    public static final String COMPENSATEAMOUNT_VALUE = "CompensateAmount";
    public static final String SELLTOTALAMOUNT_VALUE = "SellTotalAmount";

    public static final SHEDataTypeEnum TotalCount = new SHEDataTypeEnum("TotalCount", TOTALCOUNT_VALUE);
    public static final SHEDataTypeEnum TotalBuildingArea = new SHEDataTypeEnum("TotalBuildingArea", TOTALBUILDINGAREA_VALUE);
    public static final SHEDataTypeEnum TotalRoomArea = new SHEDataTypeEnum("TotalRoomArea", TOTALROOMAREA_VALUE);
    public static final SHEDataTypeEnum TotalStandardAmount = new SHEDataTypeEnum("TotalStandardAmount", TOTALSTANDARDAMOUNT_VALUE);
    public static final SHEDataTypeEnum NoSellCount = new SHEDataTypeEnum("NoSellCount", NOSELLCOUNT_VALUE);
    public static final SHEDataTypeEnum NoSellBuildingArea = new SHEDataTypeEnum("NoSellBuildingArea", NOSELLBUILDINGAREA_VALUE);
    public static final SHEDataTypeEnum NoSellRoomArea = new SHEDataTypeEnum("NoSellRoomArea", NOSELLROOMAREA_VALUE);
    public static final SHEDataTypeEnum NoSellStandardAmount = new SHEDataTypeEnum("NoSellStandardAmount", NOSELLSTANDARDAMOUNT_VALUE);
    public static final SHEDataTypeEnum SellCount = new SHEDataTypeEnum("SellCount", SELLCOUNT_VALUE);
    public static final SHEDataTypeEnum SellBuildingArea = new SHEDataTypeEnum("SellBuildingArea", SELLBUILDINGAREA_VALUE);
    public static final SHEDataTypeEnum SellRoomArea = new SHEDataTypeEnum("SellRoomArea", SELLROOMAREA_VALUE);
    public static final SHEDataTypeEnum SellStandardAmount = new SHEDataTypeEnum("SellStandardAmount", SELLSTANDARDAMOUNT_VALUE);
    public static final SHEDataTypeEnum SellDealAmount = new SHEDataTypeEnum("SellDealAmount", SELLDEALAMOUNT_VALUE);
    public static final SHEDataTypeEnum CompensateAmount = new SHEDataTypeEnum("CompensateAmount", COMPENSATEAMOUNT_VALUE);
    public static final SHEDataTypeEnum SellTotalAmount = new SHEDataTypeEnum("SellTotalAmount", SELLTOTALAMOUNT_VALUE);

    /**
     * construct function
     * @param String sHEDataTypeEnum
     */
    private SHEDataTypeEnum(String name, String sHEDataTypeEnum)
    {
        super(name, sHEDataTypeEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static SHEDataTypeEnum getEnum(String sHEDataTypeEnum)
    {
        return (SHEDataTypeEnum)getEnum(SHEDataTypeEnum.class, sHEDataTypeEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(SHEDataTypeEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(SHEDataTypeEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(SHEDataTypeEnum.class);
    }
}