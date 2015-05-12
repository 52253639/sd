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
public class DepositDealTypeEnum extends StringEnum
{
    public static final String QUIT_VALUE = "QUIT";//alias=ÍËÑº½ðÉêÇë
    public static final String OFFSET_VALUE = "OFFSET";//alias=Ñº½ð³åµÖ×â½ðÉêÇë

    public static final DepositDealTypeEnum QUIT = new DepositDealTypeEnum("QUIT", QUIT_VALUE);
    public static final DepositDealTypeEnum OFFSET = new DepositDealTypeEnum("OFFSET", OFFSET_VALUE);

    /**
     * construct function
     * @param String depositDealTypeEnum
     */
    private DepositDealTypeEnum(String name, String depositDealTypeEnum)
    {
        super(name, depositDealTypeEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static DepositDealTypeEnum getEnum(String depositDealTypeEnum)
    {
        return (DepositDealTypeEnum)getEnum(DepositDealTypeEnum.class, depositDealTypeEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(DepositDealTypeEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(DepositDealTypeEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(DepositDealTypeEnum.class);
    }
}