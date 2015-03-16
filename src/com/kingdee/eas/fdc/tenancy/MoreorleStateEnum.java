/**
 * output package name
 */
package com.kingdee.eas.fdc.tenancy;

import java.util.Map;
import java.util.List;
import java.util.Iterator;
import com.kingdee.util.enums.StringEnum;

/**
 * output class name
 */
public class MoreorleStateEnum extends StringEnum
{
    public static final String ALL_VALUE = "All";
    public static final String NEWTENANCY_VALUE = "NewTenancy";
    public static final String ENLARGETENANCY_VALUE = "EnlargeTenancy";
    public static final String REDUCETENANCY_VALUE = "ReduceTenancy";
    public static final String QUITTENANCY_VALUE = "QuitTenancy";

    public static final MoreorleStateEnum all = new MoreorleStateEnum("all", ALL_VALUE);
    public static final MoreorleStateEnum newTenancy = new MoreorleStateEnum("newTenancy", NEWTENANCY_VALUE);
    public static final MoreorleStateEnum enlargeTenancy = new MoreorleStateEnum("enlargeTenancy", ENLARGETENANCY_VALUE);
    public static final MoreorleStateEnum reduceTenancy = new MoreorleStateEnum("reduceTenancy", REDUCETENANCY_VALUE);
    public static final MoreorleStateEnum quitTenancy = new MoreorleStateEnum("quitTenancy", QUITTENANCY_VALUE);

    /**
     * construct function
     * @param String moreorleStateEnum
     */
    private MoreorleStateEnum(String name, String moreorleStateEnum)
    {
        super(name, moreorleStateEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static MoreorleStateEnum getEnum(String moreorleStateEnum)
    {
        return (MoreorleStateEnum)getEnum(MoreorleStateEnum.class, moreorleStateEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(MoreorleStateEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(MoreorleStateEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(MoreorleStateEnum.class);
    }
}