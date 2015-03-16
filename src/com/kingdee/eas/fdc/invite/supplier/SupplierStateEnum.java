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
public class SupplierStateEnum extends IntEnum
{
    public static final int SAVE_VALUE = 1;
    public static final int SUBMIT_VALUE = 2;
    public static final int APPROVE_VALUE = 3;
    public static final int UNAPPROVE_VALUE = 4;
    public static final int YEATSUBMIT_VALUE = 5;
    public static final int AUDITING_VALUE = 6;
    public static final int YEATAUDIT_VALUE = 7;

    public static final SupplierStateEnum SAVE = new SupplierStateEnum("SAVE", SAVE_VALUE);
    public static final SupplierStateEnum SUBMIT = new SupplierStateEnum("SUBMIT", SUBMIT_VALUE);
    public static final SupplierStateEnum APPROVE = new SupplierStateEnum("APPROVE", APPROVE_VALUE);
    public static final SupplierStateEnum UNAPPROVE = new SupplierStateEnum("UNAPPROVE", UNAPPROVE_VALUE);
    public static final SupplierStateEnum YEATSUBMIT = new SupplierStateEnum("YEATSUBMIT", YEATSUBMIT_VALUE);
    public static final SupplierStateEnum AUDITING = new SupplierStateEnum("AUDITING", AUDITING_VALUE);
    public static final SupplierStateEnum YEATAUDIT = new SupplierStateEnum("YEATAUDIT", YEATAUDIT_VALUE);

    /**
     * construct function
     * @param integer supplierStateEnum
     */
    private SupplierStateEnum(String name, int supplierStateEnum)
    {
        super(name, supplierStateEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static SupplierStateEnum getEnum(String supplierStateEnum)
    {
        return (SupplierStateEnum)getEnum(SupplierStateEnum.class, supplierStateEnum);
    }

    /**
     * getEnum function
     * @param String arguments
     */
    public static SupplierStateEnum getEnum(int supplierStateEnum)
    {
        return (SupplierStateEnum)getEnum(SupplierStateEnum.class, supplierStateEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(SupplierStateEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(SupplierStateEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(SupplierStateEnum.class);
    }
}