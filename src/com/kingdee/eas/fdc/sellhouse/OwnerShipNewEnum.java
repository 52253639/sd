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
public class OwnerShipNewEnum extends StringEnum
{
    public static final String OWNERHOLD_VALUE = "1OwnerHold";
    public static final String ALREADYMORTAGAGE_VALUE = "2AlreadyMortagage";
    public static final String ALREADYATTACHMENT_VALUE = "3AlreadyAttachment";
    public static final String MORTAGAGEANDATTACHMENT_VALUE = "4MortagageAndAttachment";

    public static final OwnerShipNewEnum OwnerHold = new OwnerShipNewEnum("OwnerHold", OWNERHOLD_VALUE);
    public static final OwnerShipNewEnum AlreadyMortagage = new OwnerShipNewEnum("AlreadyMortagage", ALREADYMORTAGAGE_VALUE);
    public static final OwnerShipNewEnum AlreadyAttachment = new OwnerShipNewEnum("AlreadyAttachment", ALREADYATTACHMENT_VALUE);
    public static final OwnerShipNewEnum MortagageAndAttachment = new OwnerShipNewEnum("MortagageAndAttachment", MORTAGAGEANDATTACHMENT_VALUE);

    /**
     * construct function
     * @param String ownerShipNewEnum
     */
    private OwnerShipNewEnum(String name, String ownerShipNewEnum)
    {
        super(name, ownerShipNewEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static OwnerShipNewEnum getEnum(String ownerShipNewEnum)
    {
        return (OwnerShipNewEnum)getEnum(OwnerShipNewEnum.class, ownerShipNewEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(OwnerShipNewEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(OwnerShipNewEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(OwnerShipNewEnum.class);
    }
}