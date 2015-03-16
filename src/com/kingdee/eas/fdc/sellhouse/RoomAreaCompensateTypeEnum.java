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
public class RoomAreaCompensateTypeEnum extends StringEnum
{
    public static final String BYHAND_VALUE = "byHand";
    public static final String BYSCHEME_VALUE = "byScheme";

    public static final RoomAreaCompensateTypeEnum BYHAND = new RoomAreaCompensateTypeEnum("BYHAND", BYHAND_VALUE);
    public static final RoomAreaCompensateTypeEnum BYSCHEME = new RoomAreaCompensateTypeEnum("BYSCHEME", BYSCHEME_VALUE);

    /**
     * construct function
     * @param String roomAreaCompensateTypeEnum
     */
    private RoomAreaCompensateTypeEnum(String name, String roomAreaCompensateTypeEnum)
    {
        super(name, roomAreaCompensateTypeEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static RoomAreaCompensateTypeEnum getEnum(String roomAreaCompensateTypeEnum)
    {
        return (RoomAreaCompensateTypeEnum)getEnum(RoomAreaCompensateTypeEnum.class, roomAreaCompensateTypeEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(RoomAreaCompensateTypeEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(RoomAreaCompensateTypeEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(RoomAreaCompensateTypeEnum.class);
    }
}