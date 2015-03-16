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
public class LinkmanTypeEnum extends StringEnum
{
    public static final String INDIVIDUAL_VALUE = "1Individual";
    public static final String ENTERPRISE_VALUE = "2Enterprise";

    public static final LinkmanTypeEnum Individual = new LinkmanTypeEnum("Individual", INDIVIDUAL_VALUE);
    public static final LinkmanTypeEnum Enterprise = new LinkmanTypeEnum("Enterprise", ENTERPRISE_VALUE);

    /**
     * construct function
     * @param String linkmanTypeEnum
     */
    private LinkmanTypeEnum(String name, String linkmanTypeEnum)
    {
        super(name, linkmanTypeEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static LinkmanTypeEnum getEnum(String linkmanTypeEnum)
    {
        return (LinkmanTypeEnum)getEnum(LinkmanTypeEnum.class, linkmanTypeEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(LinkmanTypeEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(LinkmanTypeEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(LinkmanTypeEnum.class);
    }
}