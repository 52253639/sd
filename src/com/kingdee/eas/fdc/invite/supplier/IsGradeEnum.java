/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.supplier;

import java.util.Map;
import java.util.List;
import java.util.Iterator;
import com.kingdee.util.enums.IntEnum;

/**
 * output class name
 */
public class IsGradeEnum extends IntEnum
{
    public static final int ELIGIBILITY_VALUE = 1;
    public static final int ENGRADE_VALUE = 2;

    public static final IsGradeEnum ELIGIBILITY = new IsGradeEnum("ELIGIBILITY", ELIGIBILITY_VALUE);
    public static final IsGradeEnum ENGRADE = new IsGradeEnum("ENGRADE", ENGRADE_VALUE);

    /**
     * construct function
     * @param integer isGradeEnum
     */
    private IsGradeEnum(String name, int isGradeEnum)
    {
        super(name, isGradeEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static IsGradeEnum getEnum(String isGradeEnum)
    {
        return (IsGradeEnum)getEnum(IsGradeEnum.class, isGradeEnum);
    }

    /**
     * getEnum function
     * @param String arguments
     */
    public static IsGradeEnum getEnum(int isGradeEnum)
    {
        return (IsGradeEnum)getEnum(IsGradeEnum.class, isGradeEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(IsGradeEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(IsGradeEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(IsGradeEnum.class);
    }
}