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
public class OpenBatchEnum extends StringEnum
{
    public static final String FIRST_VALUE = "FIRST";
    public static final String ADD_VALUE = "ADD";

    public static final OpenBatchEnum FIRST = new OpenBatchEnum("FIRST", FIRST_VALUE);
    public static final OpenBatchEnum ADD = new OpenBatchEnum("ADD", ADD_VALUE);

    /**
     * construct function
     * @param String openBatchEnum
     */
    private OpenBatchEnum(String name, String openBatchEnum)
    {
        super(name, openBatchEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static OpenBatchEnum getEnum(String openBatchEnum)
    {
        return (OpenBatchEnum)getEnum(OpenBatchEnum.class, openBatchEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(OpenBatchEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(OpenBatchEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(OpenBatchEnum.class);
    }
}