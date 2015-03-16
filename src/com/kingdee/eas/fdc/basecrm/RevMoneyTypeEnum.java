/**
 * output package name
 */
package com.kingdee.eas.fdc.basecrm;

import java.util.Map;
import java.util.List;
import java.util.Iterator;
import com.kingdee.util.enums.StringEnum;

/**
 * output class name
 */
public class RevMoneyTypeEnum extends StringEnum
{
    public static final String PREREV_VALUE = "PreRev";
    public static final String APPREV_VALUE = "AppRev";
    public static final String DIRECTREV_VALUE = "DirectRev";

    public static final RevMoneyTypeEnum PreRev = new RevMoneyTypeEnum("PreRev", PREREV_VALUE);
    public static final RevMoneyTypeEnum AppRev = new RevMoneyTypeEnum("AppRev", APPREV_VALUE);
    public static final RevMoneyTypeEnum DirectRev = new RevMoneyTypeEnum("DirectRev", DIRECTREV_VALUE);

    /**
     * construct function
     * @param String revMoneyTypeEnum
     */
    private RevMoneyTypeEnum(String name, String revMoneyTypeEnum)
    {
        super(name, revMoneyTypeEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static RevMoneyTypeEnum getEnum(String revMoneyTypeEnum)
    {
        return (RevMoneyTypeEnum)getEnum(RevMoneyTypeEnum.class, revMoneyTypeEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(RevMoneyTypeEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(RevMoneyTypeEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(RevMoneyTypeEnum.class);
    }
}