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
public class SettleMentTypeEnum extends StringEnum
{
    public static final String PREPURSETTLE_VALUE = "prePurSettle";
    public static final String CHANGEROOMSETTLE_VALUE = "changeRoomSettle";
    public static final String QUITROOMSETTLE_VALUE = "quitRoomSettle";

    public static final SettleMentTypeEnum prePurSettle = new SettleMentTypeEnum("prePurSettle", PREPURSETTLE_VALUE);
    public static final SettleMentTypeEnum changeRoomSettle = new SettleMentTypeEnum("changeRoomSettle", CHANGEROOMSETTLE_VALUE);
    public static final SettleMentTypeEnum quitRoomSettle = new SettleMentTypeEnum("quitRoomSettle", QUITROOMSETTLE_VALUE);

    /**
     * construct function
     * @param String settleMentTypeEnum
     */
    private SettleMentTypeEnum(String name, String settleMentTypeEnum)
    {
        super(name, settleMentTypeEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static SettleMentTypeEnum getEnum(String settleMentTypeEnum)
    {
        return (SettleMentTypeEnum)getEnum(SettleMentTypeEnum.class, settleMentTypeEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(SettleMentTypeEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(SettleMentTypeEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(SettleMentTypeEnum.class);
    }
}