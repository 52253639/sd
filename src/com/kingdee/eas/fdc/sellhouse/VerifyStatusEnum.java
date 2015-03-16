/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse;

import java.util.Map;
import java.util.List;
import java.util.Iterator;
import com.kingdee.util.enums.IntEnum;

/**
 * output class name
 */
public class VerifyStatusEnum extends IntEnum
{
    public static final int NOTVERIFIED_VALUE = 0;
    public static final int VERIFIED_VALUE = 1;

    public static final VerifyStatusEnum NotVerified = new VerifyStatusEnum("NotVerified", NOTVERIFIED_VALUE);
    public static final VerifyStatusEnum Verified = new VerifyStatusEnum("Verified", VERIFIED_VALUE);

    /**
     * construct function
     * @param integer verifyStatusEnum
     */
    private VerifyStatusEnum(String name, int verifyStatusEnum)
    {
        super(name, verifyStatusEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static VerifyStatusEnum getEnum(String verifyStatusEnum)
    {
        return (VerifyStatusEnum)getEnum(VerifyStatusEnum.class, verifyStatusEnum);
    }

    /**
     * getEnum function
     * @param String arguments
     */
    public static VerifyStatusEnum getEnum(int verifyStatusEnum)
    {
        return (VerifyStatusEnum)getEnum(VerifyStatusEnum.class, verifyStatusEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(VerifyStatusEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(VerifyStatusEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(VerifyStatusEnum.class);
    }
}