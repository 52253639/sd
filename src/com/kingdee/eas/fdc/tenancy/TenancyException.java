/**
 * output package name
 */
package com.kingdee.eas.fdc.tenancy;

import com.kingdee.util.NumericExceptionSubItem;
import com.kingdee.eas.common.EASBizException;

/**
 * output class name
 */
public class TenancyException extends EASBizException
{
    private static final String MAINCODE = "90";

    public static final NumericExceptionSubItem NAME_DUP = new NumericExceptionSubItem("000", "NAME_DUP");
    public static final NumericExceptionSubItem NUMBER_DUP = new NumericExceptionSubItem("001", "NUMBER_DUP");
    public static final NumericExceptionSubItem DEPOSIT_NOT_ENOUGH = new NumericExceptionSubItem("002", "DEPOSIT_NOT_ENOUGH");

    /**
     * construct function
     * @param NumericExceptionSubItem info
     * @param Throwable cause
     * @param Object[] params
     */
    public TenancyException(NumericExceptionSubItem info, Throwable cause, Object[] params)
    {
        super(info, cause, params);
    }

    /**
     * construct function
     * @param NumericExceptionSubItem info,Object[] params
     */
    public TenancyException(NumericExceptionSubItem info, Object[] params)
    {
        this(info, null, params);
    }

    /**
     * construct function
     * @param NumericExceptionSubItem info,Throwable cause
     */
    public TenancyException(NumericExceptionSubItem info, Throwable cause)
    {
        this(info, cause, null);
    }

    /**
     * construct function
     * @param NumericExceptionSubItem info
     */
    public TenancyException(NumericExceptionSubItem info)
    {
        this(info, null, null);
    }

    /**
     * getMainCode function
     */
    public String getMainCode()
    {
        return MAINCODE;
    }
}