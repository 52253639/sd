/**
 * output package name
 */
package com.kingdee.eas.fdc.basecrm;

import java.util.Map;
import java.util.List;
import java.util.Iterator;
import com.kingdee.util.enums.StringEnum;

/**
 * output class name
 */
public class AssociationTypeEnum extends StringEnum
{
    public static final String HAND_VALUE = "1HAND";
    public static final String SYSTEM_VALUE = "2SYSTEM";
    public static final String NULL_VALUE = "3NULL";

    public static final AssociationTypeEnum HAND = new AssociationTypeEnum("HAND", HAND_VALUE);
    public static final AssociationTypeEnum SYSTEM = new AssociationTypeEnum("SYSTEM", SYSTEM_VALUE);
    public static final AssociationTypeEnum NULL = new AssociationTypeEnum("NULL", NULL_VALUE);

    /**
     * construct function
     * @param String associationTypeEnum
     */
    private AssociationTypeEnum(String name, String associationTypeEnum)
    {
        super(name, associationTypeEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static AssociationTypeEnum getEnum(String associationTypeEnum)
    {
        return (AssociationTypeEnum)getEnum(AssociationTypeEnum.class, associationTypeEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(AssociationTypeEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(AssociationTypeEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(AssociationTypeEnum.class);
    }
}