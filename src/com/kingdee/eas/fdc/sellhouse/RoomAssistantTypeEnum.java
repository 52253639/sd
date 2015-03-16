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
public class RoomAssistantTypeEnum extends StringEnum
{
    public static final String SINGLE_VALUE = "1SINGLE";
    public static final String MUTIPLE_VALUE = "2MUTIPLE";

    public static final RoomAssistantTypeEnum Single = new RoomAssistantTypeEnum("Single", SINGLE_VALUE);
    public static final RoomAssistantTypeEnum Mutiple = new RoomAssistantTypeEnum("Mutiple", MUTIPLE_VALUE);

    /**
     * construct function
     * @param String roomAssistantTypeEnum
     */
    private RoomAssistantTypeEnum(String name, String roomAssistantTypeEnum)
    {
        super(name, roomAssistantTypeEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static RoomAssistantTypeEnum getEnum(String roomAssistantTypeEnum)
    {
        return (RoomAssistantTypeEnum)getEnum(RoomAssistantTypeEnum.class, roomAssistantTypeEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(RoomAssistantTypeEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(RoomAssistantTypeEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(RoomAssistantTypeEnum.class);
    }
}