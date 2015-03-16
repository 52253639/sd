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
public class GatherTypeEnum extends StringEnum
{
    public static final String BILLGATHER_VALUE = "BillGather";
    public static final String RECEIVEGATHER_VALUE = "ReceiveGather";
    public static final String REFUMENTGATHER_VALUE = "RefumentGather";

    public static final GatherTypeEnum BillGather = new GatherTypeEnum("BillGather", BILLGATHER_VALUE);
    public static final GatherTypeEnum ReceiveGather = new GatherTypeEnum("ReceiveGather", RECEIVEGATHER_VALUE);
    public static final GatherTypeEnum RefumentGather = new GatherTypeEnum("RefumentGather", REFUMENTGATHER_VALUE);

    /**
     * construct function
     * @param String gatherTypeEnum
     */
    private GatherTypeEnum(String name, String gatherTypeEnum)
    {
        super(name, gatherTypeEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static GatherTypeEnum getEnum(String gatherTypeEnum)
    {
        return (GatherTypeEnum)getEnum(GatherTypeEnum.class, gatherTypeEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(GatherTypeEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(GatherTypeEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(GatherTypeEnum.class);
    }
}