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
public class ChequeStatusEnum extends IntEnum
{
    public static final int BOOKED_VALUE = 0;
    public static final int WRITTENOFF_VALUE = 1;
    public static final int VERIFIED_VALUE = 2;
    public static final int CANCELLED_VALUE = 3;
    public static final int PICKED_VALUE = 4;
    public static final int VC_VALUE = 5;

    public static final ChequeStatusEnum Booked = new ChequeStatusEnum("Booked", BOOKED_VALUE);
    public static final ChequeStatusEnum WrittenOff = new ChequeStatusEnum("WrittenOff", WRITTENOFF_VALUE);
    public static final ChequeStatusEnum Verified = new ChequeStatusEnum("Verified", VERIFIED_VALUE);
    public static final ChequeStatusEnum Cancelled = new ChequeStatusEnum("Cancelled", CANCELLED_VALUE);
    public static final ChequeStatusEnum Picked = new ChequeStatusEnum("Picked", PICKED_VALUE);
    public static final ChequeStatusEnum VC = new ChequeStatusEnum("VC", VC_VALUE);

    /**
     * construct function
     * @param integer chequeStatusEnum
     */
    private ChequeStatusEnum(String name, int chequeStatusEnum)
    {
        super(name, chequeStatusEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static ChequeStatusEnum getEnum(String chequeStatusEnum)
    {
        return (ChequeStatusEnum)getEnum(ChequeStatusEnum.class, chequeStatusEnum);
    }

    /**
     * getEnum function
     * @param String arguments
     */
    public static ChequeStatusEnum getEnum(int chequeStatusEnum)
    {
        return (ChequeStatusEnum)getEnum(ChequeStatusEnum.class, chequeStatusEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(ChequeStatusEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(ChequeStatusEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(ChequeStatusEnum.class);
    }
}