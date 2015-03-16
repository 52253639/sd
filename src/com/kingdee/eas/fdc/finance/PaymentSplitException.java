/**
 * output package name
 */
package com.kingdee.eas.fdc.finance;

import com.kingdee.util.NumericExceptionSubItem;
import com.kingdee.eas.common.EASBizException;

/**
 * output class name
 */
public class PaymentSplitException extends EASBizException
{
    private static final String MAINCODE = "53";

    public static final NumericExceptionSubItem NO_ACCOUNT = new NumericExceptionSubItem("000", "NO_ACCOUNT");
    public static final NumericExceptionSubItem ACCOUNT_WRONG = new NumericExceptionSubItem("001", "ACCOUNT_WRONG");
    public static final NumericExceptionSubItem HAD_SETTLE = new NumericExceptionSubItem("002", "HAD_SETTLE");
    public static final NumericExceptionSubItem HAS_NOFINCLOSEPRJ = new NumericExceptionSubItem("003", "HAS_NOFINClOSEPRJ");
    public static final NumericExceptionSubItem HAS_PRODUCTSETT = new NumericExceptionSubItem("004", "HAS_PRODUCTSETT");
    public static final NumericExceptionSubItem HAS_REFERED = new NumericExceptionSubItem("005", "HAS_REFERED");
    public static final NumericExceptionSubItem HASCOSTACCOUNTNOTMAPACCOUNT = new NumericExceptionSubItem("006", "HASCOSTACCOUNTNOTMAPACCOUNT");
    public static final NumericExceptionSubItem NOTSETADJUSTCOLDINGRULE = new NumericExceptionSubItem("007", "NOTSETADJUSTCOLDINGRULE");
    public static final NumericExceptionSubItem CANNOTUNAUDIT = new NumericExceptionSubItem("008", "CANNOTUNAUDIT");
    public static final NumericExceptionSubItem HASCOSTACCOUNTNOTMAPINVOICEACCOUNT = new NumericExceptionSubItem("009", "HASCOSTACCOUNTNOTMAPINVOICEACCOUNT");

    /**
     * construct function
     * @param NumericExceptionSubItem info
     * @param Throwable cause
     * @param Object[] params
     */
    public PaymentSplitException(NumericExceptionSubItem info, Throwable cause, Object[] params)
    {
        super(info, cause, params);
    }

    /**
     * construct function
     * @param NumericExceptionSubItem info,Object[] params
     */
    public PaymentSplitException(NumericExceptionSubItem info, Object[] params)
    {
        this(info, null, params);
    }

    /**
     * construct function
     * @param NumericExceptionSubItem info,Throwable cause
     */
    public PaymentSplitException(NumericExceptionSubItem info, Throwable cause)
    {
        this(info, cause, null);
    }

    /**
     * construct function
     * @param NumericExceptionSubItem info
     */
    public PaymentSplitException(NumericExceptionSubItem info)
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