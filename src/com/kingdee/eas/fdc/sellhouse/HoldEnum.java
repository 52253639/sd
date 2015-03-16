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
public class HoldEnum extends StringEnum
{
    public static final String YUAN_VALUE = "YUAN";
    public static final String JIAO_VALUE = "JIAO";
    public static final String FEN_VALUE = "FEN";

    public static final HoldEnum YUAN = new HoldEnum("YUAN", YUAN_VALUE);
    public static final HoldEnum JIAO = new HoldEnum("JIAO", JIAO_VALUE);
    public static final HoldEnum FEN = new HoldEnum("FEN", FEN_VALUE);

    /**
     * construct function
     * @param String holdEnum
     */
    private HoldEnum(String name, String holdEnum)
    {
        super(name, holdEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static HoldEnum getEnum(String holdEnum)
    {
        return (HoldEnum)getEnum(HoldEnum.class, holdEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(HoldEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(HoldEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(HoldEnum.class);
    }
}