/**
 * output package name
 */
package com.kingdee.eas.fdc.basecrm;

import java.util.Map;
import java.util.List;
import java.util.Iterator;
import com.kingdee.util.enums.IntEnum;

/**
 * output class name
 */
public class RevBillStatusEnum extends IntEnum
{
    public static final int SAVE_VALUE = 10;
    public static final int SUBMIT_VALUE = 11;
    public static final int AUDITED_VALUE = 12;
    public static final int RECED_VALUE = 14;
    public static final int AUDITING_VALUE = 6;

    public static final RevBillStatusEnum SAVE = new RevBillStatusEnum("SAVE", SAVE_VALUE);
    public static final RevBillStatusEnum SUBMIT = new RevBillStatusEnum("SUBMIT", SUBMIT_VALUE);
    public static final RevBillStatusEnum AUDITED = new RevBillStatusEnum("AUDITED", AUDITED_VALUE);
    public static final RevBillStatusEnum RECED = new RevBillStatusEnum("RECED", RECED_VALUE);
    public static final RevBillStatusEnum AUDITING = new RevBillStatusEnum("AUDITING", AUDITING_VALUE);

    /**
     * construct function
     * @param integer revBillStatusEnum
     */
    private RevBillStatusEnum(String name, int revBillStatusEnum)
    {
        super(name, revBillStatusEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static RevBillStatusEnum getEnum(String revBillStatusEnum)
    {
        return (RevBillStatusEnum)getEnum(RevBillStatusEnum.class, revBillStatusEnum);
    }

    /**
     * getEnum function
     * @param String arguments
     */
    public static RevBillStatusEnum getEnum(int revBillStatusEnum)
    {
        return (RevBillStatusEnum)getEnum(RevBillStatusEnum.class, revBillStatusEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(RevBillStatusEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(RevBillStatusEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(RevBillStatusEnum.class);
    }
}