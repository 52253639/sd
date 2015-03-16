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
public class CluesSourceEnum extends StringEnum
{
    public static final String CALL_VALUE = "1CALL";
    public static final String VISIT_VALUE = "2VISIT";
    public static final String OTHER_VALUE = "3OTHER";
    public static final String CHOOCEROOM_VALUE = "4CHOOCEROOM";
    public static final String SINCERITYPRUCHASE_VALUE = "5SINCERITYPRUCHASE";

    public static final CluesSourceEnum CALL = new CluesSourceEnum("CALL", CALL_VALUE);
    public static final CluesSourceEnum VISIT = new CluesSourceEnum("VISIT", VISIT_VALUE);
    public static final CluesSourceEnum OTHER = new CluesSourceEnum("OTHER", OTHER_VALUE);
    public static final CluesSourceEnum CHOOCEROOM = new CluesSourceEnum("CHOOCEROOM", CHOOCEROOM_VALUE);
    public static final CluesSourceEnum SINCERITYPRUCHASE = new CluesSourceEnum("SINCERITYPRUCHASE", SINCERITYPRUCHASE_VALUE);

    /**
     * construct function
     * @param String cluesSourceEnum
     */
    private CluesSourceEnum(String name, String cluesSourceEnum)
    {
        super(name, cluesSourceEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static CluesSourceEnum getEnum(String cluesSourceEnum)
    {
        return (CluesSourceEnum)getEnum(CluesSourceEnum.class, cluesSourceEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(CluesSourceEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(CluesSourceEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(CluesSourceEnum.class);
    }
}