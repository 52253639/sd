/**
 * output package name
 */
package com.kingdee.eas.fdc.market;

import java.util.Map;
import java.util.List;
import java.util.Iterator;
import com.kingdee.util.enums.StringEnum;

/**
 * output class name
 */
public class DocSubItemTypeEnum extends StringEnum
{
    public static final String MANUAL_VALUE = "Manual";
    public static final String RELATION_VALUE = "Relation";

    public static final DocSubItemTypeEnum Manual = new DocSubItemTypeEnum("Manual", MANUAL_VALUE);
    public static final DocSubItemTypeEnum Relation = new DocSubItemTypeEnum("Relation", RELATION_VALUE);

    /**
     * construct function
     * @param String docSubItemTypeEnum
     */
    private DocSubItemTypeEnum(String name, String docSubItemTypeEnum)
    {
        super(name, docSubItemTypeEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static DocSubItemTypeEnum getEnum(String docSubItemTypeEnum)
    {
        return (DocSubItemTypeEnum)getEnum(DocSubItemTypeEnum.class, docSubItemTypeEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(DocSubItemTypeEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(DocSubItemTypeEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(DocSubItemTypeEnum.class);
    }
}