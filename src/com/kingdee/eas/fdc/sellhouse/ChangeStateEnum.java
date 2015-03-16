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
public class ChangeStateEnum extends StringEnum
{
    public static final String CHANGEROOM_VALUE = "ChangeRoom";
    public static final String QUITROOM_VALUE = "QuitRoom";
    public static final String CHANGEAREA_VALUE = "ChangeArea";

    public static final ChangeStateEnum ChangeRoom = new ChangeStateEnum("ChangeRoom", CHANGEROOM_VALUE);
    public static final ChangeStateEnum QuitRoom = new ChangeStateEnum("QuitRoom", QUITROOM_VALUE);
    public static final ChangeStateEnum ChangeArea = new ChangeStateEnum("ChangeArea", CHANGEAREA_VALUE);

    /**
     * construct function
     * @param String changeStateEnum
     */
    private ChangeStateEnum(String name, String changeStateEnum)
    {
        super(name, changeStateEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static ChangeStateEnum getEnum(String changeStateEnum)
    {
        return (ChangeStateEnum)getEnum(ChangeStateEnum.class, changeStateEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(ChangeStateEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(ChangeStateEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(ChangeStateEnum.class);
    }
}