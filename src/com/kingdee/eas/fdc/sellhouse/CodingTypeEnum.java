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
public class CodingTypeEnum extends StringEnum
{
    public static final String NUM_VALUE = "Num";
    public static final String FLOORNUM_VALUE = "FloorNum";
    public static final String UNITFLOORNUM_VALUE = "UnitFloorNum";

    public static final CodingTypeEnum Num = new CodingTypeEnum("Num", NUM_VALUE);
    public static final CodingTypeEnum FloorNum = new CodingTypeEnum("FloorNum", FLOORNUM_VALUE);
    public static final CodingTypeEnum UnitFloorNum = new CodingTypeEnum("UnitFloorNum", UNITFLOORNUM_VALUE);

    /**
     * construct function
     * @param String codingTypeEnum
     */
    private CodingTypeEnum(String name, String codingTypeEnum)
    {
        super(name, codingTypeEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static CodingTypeEnum getEnum(String codingTypeEnum)
    {
        return (CodingTypeEnum)getEnum(CodingTypeEnum.class, codingTypeEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(CodingTypeEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(CodingTypeEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(CodingTypeEnum.class);
    }
}