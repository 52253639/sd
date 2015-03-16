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
public class NetWorkTypeEnum extends StringEnum
{
    public static final String TELECOM_VALUE = "1TELECOM";
    public static final String RETICLE_VALUE = "2RETICLE";
    public static final String ONLINE_VALUE = "3ONLINE";
    public static final String TIETONG_VALUE = "4TIETONG";
    public static final String OTHER_VALUE = "5OTHER";

    public static final NetWorkTypeEnum TELECOM = new NetWorkTypeEnum("TELECOM", TELECOM_VALUE);
    public static final NetWorkTypeEnum RETICLE = new NetWorkTypeEnum("RETICLE", RETICLE_VALUE);
    public static final NetWorkTypeEnum ONLINE = new NetWorkTypeEnum("ONLINE", ONLINE_VALUE);
    public static final NetWorkTypeEnum TIETONG = new NetWorkTypeEnum("TIETONG", TIETONG_VALUE);
    public static final NetWorkTypeEnum OTHER = new NetWorkTypeEnum("OTHER", OTHER_VALUE);

    /**
     * construct function
     * @param String netWorkTypeEnum
     */
    private NetWorkTypeEnum(String name, String netWorkTypeEnum)
    {
        super(name, netWorkTypeEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static NetWorkTypeEnum getEnum(String netWorkTypeEnum)
    {
        return (NetWorkTypeEnum)getEnum(NetWorkTypeEnum.class, netWorkTypeEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(NetWorkTypeEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(NetWorkTypeEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(NetWorkTypeEnum.class);
    }
}