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
public class QuitRoomStateEnum extends StringEnum
{
    public static final String PRECONCERTQUITROOM_VALUE = "PreconcertQuitRoom";
    public static final String TAKEUPQUITROOM_VALUE = "TakeUpQuitRoom";
    public static final String SIGNUPQUITROOM_VALUE = "SignUpQuitRoom";

    public static final QuitRoomStateEnum PreconcertQuitRoom = new QuitRoomStateEnum("PreconcertQuitRoom", PRECONCERTQUITROOM_VALUE);
    public static final QuitRoomStateEnum TakeUpQuitRoom = new QuitRoomStateEnum("TakeUpQuitRoom", TAKEUPQUITROOM_VALUE);
    public static final QuitRoomStateEnum SignUpQuitRoom = new QuitRoomStateEnum("SignUpQuitRoom", SIGNUPQUITROOM_VALUE);

    /**
     * construct function
     * @param String quitRoomStateEnum
     */
    private QuitRoomStateEnum(String name, String quitRoomStateEnum)
    {
        super(name, quitRoomStateEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static QuitRoomStateEnum getEnum(String quitRoomStateEnum)
    {
        return (QuitRoomStateEnum)getEnum(QuitRoomStateEnum.class, quitRoomStateEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(QuitRoomStateEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(QuitRoomStateEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(QuitRoomStateEnum.class);
    }
}