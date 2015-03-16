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
public class RoomKeepDownBizEnum extends StringEnum
{
    public static final String KEEPDOWN_VALUE = "1KEEPDOWN";
    public static final String CANCELKEEPDOWN_VALUE = "2CANCELKEEPDOWN";
    public static final String SUBMITTED_VALUE = "3SUBMITTED";
    public static final String AUDITTED_VALUE = "4AUDITTED";
    public static final String AUDITTING_VALUE = "4AUDITTING";

    public static final RoomKeepDownBizEnum KeepDown = new RoomKeepDownBizEnum("KeepDown", KEEPDOWN_VALUE);
    public static final RoomKeepDownBizEnum CancelKeepDown = new RoomKeepDownBizEnum("CancelKeepDown", CANCELKEEPDOWN_VALUE);
    public static final RoomKeepDownBizEnum Submitted = new RoomKeepDownBizEnum("Submitted", SUBMITTED_VALUE);
    public static final RoomKeepDownBizEnum Auditted = new RoomKeepDownBizEnum("Auditted", AUDITTED_VALUE);
    public static final RoomKeepDownBizEnum Auditting = new RoomKeepDownBizEnum("Auditting", AUDITTING_VALUE);

    /**
     * construct function
     * @param String roomKeepDownBizEnum
     */
    private RoomKeepDownBizEnum(String name, String roomKeepDownBizEnum)
    {
        super(name, roomKeepDownBizEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static RoomKeepDownBizEnum getEnum(String roomKeepDownBizEnum)
    {
        return (RoomKeepDownBizEnum)getEnum(RoomKeepDownBizEnum.class, roomKeepDownBizEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(RoomKeepDownBizEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(RoomKeepDownBizEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(RoomKeepDownBizEnum.class);
    }
}