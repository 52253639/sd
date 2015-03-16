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
public class FeeFromTypeEnum extends StringEnum
{
    public static final String PRERECIVELIST_VALUE = "1PreReciveList";
    public static final String SHOULDPAYLIST_VALUE = "2ShouldPayList";
    public static final String ELSEPAYLIST_VALUE = "3ElsePayList";

    public static final FeeFromTypeEnum PreReciveList = new FeeFromTypeEnum("PreReciveList", PRERECIVELIST_VALUE);
    public static final FeeFromTypeEnum ShouldPayList = new FeeFromTypeEnum("ShouldPayList", SHOULDPAYLIST_VALUE);
    public static final FeeFromTypeEnum ElsePayList = new FeeFromTypeEnum("ElsePayList", ELSEPAYLIST_VALUE);

    /**
     * construct function
     * @param String feeFromTypeEnum
     */
    private FeeFromTypeEnum(String name, String feeFromTypeEnum)
    {
        super(name, feeFromTypeEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static FeeFromTypeEnum getEnum(String feeFromTypeEnum)
    {
        return (FeeFromTypeEnum)getEnum(FeeFromTypeEnum.class, feeFromTypeEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(FeeFromTypeEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(FeeFromTypeEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(FeeFromTypeEnum.class);
    }
}