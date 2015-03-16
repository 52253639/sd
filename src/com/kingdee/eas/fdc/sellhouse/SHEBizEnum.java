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
public class SHEBizEnum extends StringEnum
{
    public static final String PURCHASE_VALUE = "Purchase";

    public static final SHEBizEnum Purchase = new SHEBizEnum("Purchase", PURCHASE_VALUE);

    /**
     * construct function
     * @param String sHEBizEnum
     */
    private SHEBizEnum(String name, String sHEBizEnum)
    {
        super(name, sHEBizEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static SHEBizEnum getEnum(String sHEBizEnum)
    {
        return (SHEBizEnum)getEnum(SHEBizEnum.class, sHEBizEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(SHEBizEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(SHEBizEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(SHEBizEnum.class);
    }
}