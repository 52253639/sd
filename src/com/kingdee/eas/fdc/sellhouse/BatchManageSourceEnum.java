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
public class BatchManageSourceEnum extends StringEnum
{
    public static final String PROPERTY_VALUE = "0property";
    public static final String JOIN_VALUE = "1property";

    public static final BatchManageSourceEnum Property = new BatchManageSourceEnum("Property", PROPERTY_VALUE);
    public static final BatchManageSourceEnum Join = new BatchManageSourceEnum("Join", JOIN_VALUE);

    /**
     * construct function
     * @param String batchManageSourceEnum
     */
    private BatchManageSourceEnum(String name, String batchManageSourceEnum)
    {
        super(name, batchManageSourceEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static BatchManageSourceEnum getEnum(String batchManageSourceEnum)
    {
        return (BatchManageSourceEnum)getEnum(BatchManageSourceEnum.class, batchManageSourceEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(BatchManageSourceEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(BatchManageSourceEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(BatchManageSourceEnum.class);
    }
}