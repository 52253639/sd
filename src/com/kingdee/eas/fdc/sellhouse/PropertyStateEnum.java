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
public class PropertyStateEnum extends StringEnum
{
    public static final String CUSTOMRECEIVE_VALUE = "customReceive";
    public static final String BANKMORTGAGE_VALUE = "bankMortgage";
    public static final String COMPANYSTAY_VALUE = "companyStay";

    public static final PropertyStateEnum customReceive = new PropertyStateEnum("customReceive", CUSTOMRECEIVE_VALUE);
    public static final PropertyStateEnum bankMortgage = new PropertyStateEnum("bankMortgage", BANKMORTGAGE_VALUE);
    public static final PropertyStateEnum companyStay = new PropertyStateEnum("companyStay", COMPANYSTAY_VALUE);

    /**
     * construct function
     * @param String propertyStateEnum
     */
    private PropertyStateEnum(String name, String propertyStateEnum)
    {
        super(name, propertyStateEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static PropertyStateEnum getEnum(String propertyStateEnum)
    {
        return (PropertyStateEnum)getEnum(PropertyStateEnum.class, propertyStateEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(PropertyStateEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(PropertyStateEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(PropertyStateEnum.class);
    }
}