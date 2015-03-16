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
public class PropertyFeaturesEnum extends StringEnum
{
    public static final String NULL_VALUE = "NULL";
    public static final String FIRST_VALUE = "FIRST";
    public static final String SECOND_VALUE = "SECOND";
    public static final String MANY_VALUE = "MANY";

    public static final PropertyFeaturesEnum NULL = new PropertyFeaturesEnum("NULL", NULL_VALUE);
    public static final PropertyFeaturesEnum FIRST = new PropertyFeaturesEnum("FIRST", FIRST_VALUE);
    public static final PropertyFeaturesEnum SECOND = new PropertyFeaturesEnum("SECOND", SECOND_VALUE);
    public static final PropertyFeaturesEnum MANY = new PropertyFeaturesEnum("MANY", MANY_VALUE);

    /**
     * construct function
     * @param String propertyFeaturesEnum
     */
    private PropertyFeaturesEnum(String name, String propertyFeaturesEnum)
    {
        super(name, propertyFeaturesEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static PropertyFeaturesEnum getEnum(String propertyFeaturesEnum)
    {
        return (PropertyFeaturesEnum)getEnum(PropertyFeaturesEnum.class, propertyFeaturesEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(PropertyFeaturesEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(PropertyFeaturesEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(PropertyFeaturesEnum.class);
    }
}