/**
 * output package name
 */
package com.kingdee.eas.fdc.invite;

import java.util.Map;
import java.util.List;
import java.util.Iterator;
import com.kingdee.util.enums.StringEnum;

/**
 * output class name
 */
public class DrawingDepthEnum extends StringEnum
{
    public static final String SGT_VALUE = "10";
    public static final String FAT_VALUE = "20";
    public static final String KCT_VALUE = "30";

    public static final DrawingDepthEnum SGT = new DrawingDepthEnum("SGT", SGT_VALUE);
    public static final DrawingDepthEnum FAT = new DrawingDepthEnum("FAT", FAT_VALUE);
    public static final DrawingDepthEnum KCT = new DrawingDepthEnum("KCT", KCT_VALUE);

    /**
     * construct function
     * @param String drawingDepthEnum
     */
    private DrawingDepthEnum(String name, String drawingDepthEnum)
    {
        super(name, drawingDepthEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static DrawingDepthEnum getEnum(String drawingDepthEnum)
    {
        return (DrawingDepthEnum)getEnum(DrawingDepthEnum.class, drawingDepthEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(DrawingDepthEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(DrawingDepthEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(DrawingDepthEnum.class);
    }
}