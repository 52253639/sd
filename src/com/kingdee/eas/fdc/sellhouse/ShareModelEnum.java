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
public class ShareModelEnum extends StringEnum
{
    public static final String SHAREPERSON_VALUE = "sharePerson";
    public static final String SHAREMARKET_VALUE = "shareMarket";
    public static final String SHAREORGUNIT_VALUE = "shareOrgUnit";

    public static final ShareModelEnum sharePerson = new ShareModelEnum("sharePerson", SHAREPERSON_VALUE);
    public static final ShareModelEnum shareMarket = new ShareModelEnum("shareMarket", SHAREMARKET_VALUE);
    public static final ShareModelEnum shareOrgUnit = new ShareModelEnum("shareOrgUnit", SHAREORGUNIT_VALUE);

    /**
     * construct function
     * @param String shareModelEnum
     */
    private ShareModelEnum(String name, String shareModelEnum)
    {
        super(name, shareModelEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static ShareModelEnum getEnum(String shareModelEnum)
    {
        return (ShareModelEnum)getEnum(ShareModelEnum.class, shareModelEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(ShareModelEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(ShareModelEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(ShareModelEnum.class);
    }
}