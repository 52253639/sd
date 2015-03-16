/**
 * output package name
 */
package com.kingdee.eas.fdc.tenancy;

import java.util.Map;
import java.util.List;
import java.util.Iterator;
import com.kingdee.util.enums.StringEnum;

/**
 * output class name
 */
public class ChargeDateTypeEnum extends StringEnum
{
    public static final String BEFORESTARTDATE_VALUE = "1BeforeStartDate";
    public static final String AFTERSTARTDATE_VALUE = "2AfterStartDate";
    public static final String BEFOREENDDATE_VALUE = "3BeforeEndDate";
    public static final String AFTERENDDATE_VALUE = "4AfterEndDate";
    public static final String FIXEDDATE_VALUE = "5fixedDate";

    public static final ChargeDateTypeEnum BeforeStartDate = new ChargeDateTypeEnum("BeforeStartDate", BEFORESTARTDATE_VALUE);
    public static final ChargeDateTypeEnum AfterStartDate = new ChargeDateTypeEnum("AfterStartDate", AFTERSTARTDATE_VALUE);
    public static final ChargeDateTypeEnum BeforeEndDate = new ChargeDateTypeEnum("BeforeEndDate", BEFOREENDDATE_VALUE);
    public static final ChargeDateTypeEnum AfterEndDate = new ChargeDateTypeEnum("AfterEndDate", AFTERENDDATE_VALUE);
    public static final ChargeDateTypeEnum FixedDate = new ChargeDateTypeEnum("FixedDate", FIXEDDATE_VALUE);

    /**
     * construct function
     * @param String chargeDateTypeEnum
     */
    private ChargeDateTypeEnum(String name, String chargeDateTypeEnum)
    {
        super(name, chargeDateTypeEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static ChargeDateTypeEnum getEnum(String chargeDateTypeEnum)
    {
        return (ChargeDateTypeEnum)getEnum(ChargeDateTypeEnum.class, chargeDateTypeEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(ChargeDateTypeEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(ChargeDateTypeEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(ChargeDateTypeEnum.class);
    }
}