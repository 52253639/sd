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
public class WebMetaTypeEnum extends StringEnum
{
    public static final String INPUT_VALUE = "InputTag.class";
    public static final String SELECT_VALUE = "SelectTag.class";

    public static final WebMetaTypeEnum input = new WebMetaTypeEnum("input", INPUT_VALUE);
    public static final WebMetaTypeEnum select = new WebMetaTypeEnum("select", SELECT_VALUE);

    /**
     * construct function
     * @param String webMetaTypeEnum
     */
    private WebMetaTypeEnum(String name, String webMetaTypeEnum)
    {
        super(name, webMetaTypeEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static WebMetaTypeEnum getEnum(String webMetaTypeEnum)
    {
        return (WebMetaTypeEnum)getEnum(WebMetaTypeEnum.class, webMetaTypeEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(WebMetaTypeEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(WebMetaTypeEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(WebMetaTypeEnum.class);
    }
}