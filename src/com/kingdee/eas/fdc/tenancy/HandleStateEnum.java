/**
 * output package name
 */
package com.kingdee.eas.fdc.tenancy;

import java.util.Map;
import java.util.List;
import java.util.Iterator;
import com.kingdee.util.enums.StringEnum;

/**
 * output class name
 */
public class HandleStateEnum extends StringEnum
{
    public static final String NOHANDLEROOM_VALUE = "NoHandleRoom";
    public static final String HANDLINGROOM_VALUE = "HandlingRoom";
    public static final String LIVINGHOUSE_VALUE = "livingHouse";
    public static final String CALLBACKING_VALUE = "CallBacking";
    public static final String ALREADYCALLBACK_VALUE = "AlreadyCallBack";

    public static final HandleStateEnum NoHandleRoom = new HandleStateEnum("NoHandleRoom", NOHANDLEROOM_VALUE);
    public static final HandleStateEnum HandlingRoom = new HandleStateEnum("HandlingRoom", HANDLINGROOM_VALUE);
    public static final HandleStateEnum livingHouse = new HandleStateEnum("livingHouse", LIVINGHOUSE_VALUE);
    public static final HandleStateEnum CallBacking = new HandleStateEnum("CallBacking", CALLBACKING_VALUE);
    public static final HandleStateEnum AlreadyCallBack = new HandleStateEnum("AlreadyCallBack", ALREADYCALLBACK_VALUE);

    /**
     * construct function
     * @param String handleStateEnum
     */
    private HandleStateEnum(String name, String handleStateEnum)
    {
        super(name, handleStateEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static HandleStateEnum getEnum(String handleStateEnum)
    {
        return (HandleStateEnum)getEnum(HandleStateEnum.class, handleStateEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(HandleStateEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(HandleStateEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(HandleStateEnum.class);
    }
}