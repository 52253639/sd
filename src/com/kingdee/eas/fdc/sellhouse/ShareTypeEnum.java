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
public class ShareTypeEnum extends StringEnum
{
    public static final String USERSHARE_VALUE = "0";
    public static final String PROJECTSHARE_VALUE = "1";

    public static final ShareTypeEnum USERSHARE = new ShareTypeEnum("USERSHARE", USERSHARE_VALUE);
    public static final ShareTypeEnum PROJECTSHARE = new ShareTypeEnum("PROJECTSHARE", PROJECTSHARE_VALUE);

    /**
     * construct function
     * @param String shareTypeEnum
     */
    private ShareTypeEnum(String name, String shareTypeEnum)
    {
        super(name, shareTypeEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static ShareTypeEnum getEnum(String shareTypeEnum)
    {
        return (ShareTypeEnum)getEnum(ShareTypeEnum.class, shareTypeEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(ShareTypeEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(ShareTypeEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(ShareTypeEnum.class);
    }
}