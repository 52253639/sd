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
public class CreateWayEnum extends StringEnum
{
    public static final String HAND_VALUE = "HAND";
    public static final String CHILDGENERATE_VALUE = "CHILDGENERATE";
    public static final String PARENTREFERENCE_VALUE = "PARENTREFERENCE";
    public static final String UNITSHARE_VALUE = "UNITSHARE";
    public static final String SYSTEMIMPORT_VALUE = "SYSTEMIMPORT";

    public static final CreateWayEnum HAND = new CreateWayEnum("HAND", HAND_VALUE);
    public static final CreateWayEnum CHILDGENERATE = new CreateWayEnum("CHILDGENERATE", CHILDGENERATE_VALUE);
    public static final CreateWayEnum PARENTREFERENCE = new CreateWayEnum("PARENTREFERENCE", PARENTREFERENCE_VALUE);
    public static final CreateWayEnum UNITSHARE = new CreateWayEnum("UNITSHARE", UNITSHARE_VALUE);
    public static final CreateWayEnum SYSTEMIMPORT = new CreateWayEnum("SYSTEMIMPORT", SYSTEMIMPORT_VALUE);

    /**
     * construct function
     * @param String createWayEnum
     */
    private CreateWayEnum(String name, String createWayEnum)
    {
        super(name, createWayEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static CreateWayEnum getEnum(String createWayEnum)
    {
        return (CreateWayEnum)getEnum(CreateWayEnum.class, createWayEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(CreateWayEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(CreateWayEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(CreateWayEnum.class);
    }
}