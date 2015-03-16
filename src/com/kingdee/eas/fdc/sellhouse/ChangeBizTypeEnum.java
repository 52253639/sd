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
public class ChangeBizTypeEnum extends StringEnum
{
    public static final String PRICECHANGE_VALUE = "PriceChange";
    public static final String QUITROOM_VALUE = "quitRoom";
    public static final String CHANGENAME_VALUE = "ChangeName";
    public static final String CHANGEROOM_VALUE = "ChangeRoom";

    public static final ChangeBizTypeEnum PRICECHANGE = new ChangeBizTypeEnum("PRICECHANGE", PRICECHANGE_VALUE);
    public static final ChangeBizTypeEnum QUITROOM = new ChangeBizTypeEnum("QUITROOM", QUITROOM_VALUE);
    public static final ChangeBizTypeEnum CHANGENAME = new ChangeBizTypeEnum("CHANGENAME", CHANGENAME_VALUE);
    public static final ChangeBizTypeEnum CHANGEROOM = new ChangeBizTypeEnum("CHANGEROOM", CHANGEROOM_VALUE);

    /**
     * construct function
     * @param String changeBizTypeEnum
     */
    private ChangeBizTypeEnum(String name, String changeBizTypeEnum)
    {
        super(name, changeBizTypeEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static ChangeBizTypeEnum getEnum(String changeBizTypeEnum)
    {
        return (ChangeBizTypeEnum)getEnum(ChangeBizTypeEnum.class, changeBizTypeEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(ChangeBizTypeEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(ChangeBizTypeEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(ChangeBizTypeEnum.class);
    }
}