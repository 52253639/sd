/**
 * output package name
 */
package com.kingdee.eas.fdc.finance;

import com.kingdee.util.NumericExceptionSubItem;
import com.kingdee.eas.common.EASBizException;

/**
 * output class name
 */
public class FinanceException extends EASBizException
{
    private static final String MAINCODE = "11";

    public static final NumericExceptionSubItem CON_UNSPLITED = new NumericExceptionSubItem("001", "CON_UNSPLITED");
    public static final NumericExceptionSubItem CHECKWORKLOAD = new NumericExceptionSubItem("002", "CHECKWORKLOAD");
    public static final NumericExceptionSubItem ALLINVOICEAMTMORETHANCONLATESTPRICE = new NumericExceptionSubItem("003", "AllInvoiceAmtMoreThanConLatestPrice");

    /**
     * construct function
     * @param NumericExceptionSubItem info
     * @param Throwable cause
     * @param Object[] params
     */
    public FinanceException(NumericExceptionSubItem info, Throwable cause, Object[] params)
    {
        super(info, cause, params);
    }

    /**
     * construct function
     * @param NumericExceptionSubItem info,Object[] params
     */
    public FinanceException(NumericExceptionSubItem info, Object[] params)
    {
        this(info, null, params);
    }

    /**
     * construct function
     * @param NumericExceptionSubItem info,Throwable cause
     */
    public FinanceException(NumericExceptionSubItem info, Throwable cause)
    {
        this(info, cause, null);
    }

    /**
     * construct function
     * @param NumericExceptionSubItem info
     */
    public FinanceException(NumericExceptionSubItem info)
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