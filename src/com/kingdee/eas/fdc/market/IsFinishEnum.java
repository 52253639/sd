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
public class IsFinishEnum extends StringEnum
{
    public static final String UNSTART_VALUE = "UNSTART";
    public static final String FINISH_VALUE = "FINISH";
    public static final String CANCEL_VALUE = "CANCEL";

    public static final IsFinishEnum UNSTART = new IsFinishEnum("UNSTART", UNSTART_VALUE);
    public static final IsFinishEnum FINISH = new IsFinishEnum("FINISH", FINISH_VALUE);
    public static final IsFinishEnum CANCEL = new IsFinishEnum("CANCEL", CANCEL_VALUE);

    /**
     * construct function
     * @param String isFinishEnum
     */
    private IsFinishEnum(String name, String isFinishEnum)
    {
        super(name, isFinishEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static IsFinishEnum getEnum(String isFinishEnum)
    {
        return (IsFinishEnum)getEnum(IsFinishEnum.class, isFinishEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(IsFinishEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(IsFinishEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(IsFinishEnum.class);
    }
}