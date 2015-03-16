/**
 * output package name
 */
package com.kingdee.eas.fdc.invite;

import java.util.Map;
import java.util.List;
import java.util.Iterator;
import com.kingdee.util.enums.StringEnum;

/**
 * output class name
 */
public class PartABOMTypeEnum extends StringEnum
{
    public static final String PARTASUPPLY_VALUE = "0PARTASUPPLY";
    public static final String PARTAAPPOINTPARTBSUPPLY_VALUE = "1AAPPOINTBSUPPLY";

    public static final PartABOMTypeEnum PartASupply = new PartABOMTypeEnum("PartASupply", PARTASUPPLY_VALUE);
    public static final PartABOMTypeEnum PartAAppointPartBSupply = new PartABOMTypeEnum("PartAAppointPartBSupply", PARTAAPPOINTPARTBSUPPLY_VALUE);

    /**
     * construct function
     * @param String partABOMTypeEnum
     */
    private PartABOMTypeEnum(String name, String partABOMTypeEnum)
    {
        super(name, partABOMTypeEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static PartABOMTypeEnum getEnum(String partABOMTypeEnum)
    {
        return (PartABOMTypeEnum)getEnum(PartABOMTypeEnum.class, partABOMTypeEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(PartABOMTypeEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(PartABOMTypeEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(PartABOMTypeEnum.class);
    }
}