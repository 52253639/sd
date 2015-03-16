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
public class AFMortgagedStateEnum extends IntEnum
{
    public static final int UNTRANSACT_VALUE = 0;
    public static final int TRANSACTING_VALUE = 1;
    public static final int TRANSACTED_VALUE = 3;
    public static final int STOPTRANSACT_VALUE = 4;
    public static final int CHANGECALL_VALUE = 5;
    public static final int CANCELROOM_VALUE = 6;
    public static final int CHANGEROOM_VALUE = 7;
    public static final int BANKFUND_VALUE = 8;

    public static final AFMortgagedStateEnum UNTRANSACT = new AFMortgagedStateEnum("UNTRANSACT", UNTRANSACT_VALUE);
    public static final AFMortgagedStateEnum TRANSACTING = new AFMortgagedStateEnum("TRANSACTING", TRANSACTING_VALUE);
    public static final AFMortgagedStateEnum TRANSACTED = new AFMortgagedStateEnum("TRANSACTED", TRANSACTED_VALUE);
    public static final AFMortgagedStateEnum STOPTRANSACT = new AFMortgagedStateEnum("STOPTRANSACT", STOPTRANSACT_VALUE);
    public static final AFMortgagedStateEnum CHANGECALL = new AFMortgagedStateEnum("CHANGECALL", CHANGECALL_VALUE);
    public static final AFMortgagedStateEnum CANCELROOM = new AFMortgagedStateEnum("CANCELROOM", CANCELROOM_VALUE);
    public static final AFMortgagedStateEnum CHANGEROOM = new AFMortgagedStateEnum("CHANGEROOM", CHANGEROOM_VALUE);
    public static final AFMortgagedStateEnum BANKFUND = new AFMortgagedStateEnum("BANKFUND", BANKFUND_VALUE);

    /**
     * construct function
     * @param integer aFMortgagedStateEnum
     */
    private AFMortgagedStateEnum(String name, int aFMortgagedStateEnum)
    {
        super(name, aFMortgagedStateEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static AFMortgagedStateEnum getEnum(String aFMortgagedStateEnum)
    {
        return (AFMortgagedStateEnum)getEnum(AFMortgagedStateEnum.class, aFMortgagedStateEnum);
    }

    /**
     * getEnum function
     * @param String arguments
     */
    public static AFMortgagedStateEnum getEnum(int aFMortgagedStateEnum)
    {
        return (AFMortgagedStateEnum)getEnum(AFMortgagedStateEnum.class, aFMortgagedStateEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(AFMortgagedStateEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(AFMortgagedStateEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(AFMortgagedStateEnum.class);
    }
}