/**
 * output package name
 */
package com.kingdee.eas.fdc.finance;

import com.kingdee.util.NumericExceptionSubItem;
import com.kingdee.eas.common.EASBizException;

/**
 * output class name
 */
public class FDCBudgetAcctException extends EASBizException
{
    private static final String MAINCODE = "10";

    public static final NumericExceptionSubItem VERNUMBERDUP = new NumericExceptionSubItem("100", "VERNUMBERDUP");
    public static final NumericExceptionSubItem DUP = new NumericExceptionSubItem("101", "DUP");
    public static final NumericExceptionSubItem EXISTACCTPAY = new NumericExceptionSubItem("102", "EXISTACCTPAY");
    public static final NumericExceptionSubItem NOSPLITDATA = new NumericExceptionSubItem("103", "NOSPLITDATA");
    public static final NumericExceptionSubItem NOACCTPAY = new NumericExceptionSubItem("104", "NOACCTPAY");
    public static final NumericExceptionSubItem ACCTPAYNOTEQUEALREQAMT = new NumericExceptionSubItem("105", "ACCTPAYNOTEQUEALREQAMT");
    public static final NumericExceptionSubItem PERIODNODATA = new NumericExceptionSubItem("106", "PERIODNODATA");
    public static final NumericExceptionSubItem ACCTPARAM = new NumericExceptionSubItem("110", "ACCTPARAM");
    public static final NumericExceptionSubItem CONPARAM = new NumericExceptionSubItem("111", "CONPARAM");
    public static final NumericExceptionSubItem BGSYSPARAM = new NumericExceptionSubItem("112", "BGSYSPARAM");
    public static final NumericExceptionSubItem CANNTUNAUDIT = new NumericExceptionSubItem("113", "CANNTUNAUDIT");
    public static final NumericExceptionSubItem NOFITUNSETTLEDCON = new NumericExceptionSubItem("114", "NOFITUNSETTLEDCON");
    public static final NumericExceptionSubItem EXISTRECENSIONVER = new NumericExceptionSubItem("115", "EXISTRECENSIONVER");
    public static final NumericExceptionSubItem EXISTAUDITEDMONBUDGET = new NumericExceptionSubItem("116", "EXISTAUDITEDMONBUDGET");
    public static final NumericExceptionSubItem EXISTAUDITEDMONBUDGET2 = new NumericExceptionSubItem("117", "EXISTAUDITEDMONBUDGET2");
    public static final NumericExceptionSubItem EXISTDUPDEPMONBUDGET = new NumericExceptionSubItem("118", "EXISTDUPDEPMONBUDGET");
    public static final NumericExceptionSubItem EXISTUNAUDITTEDDEPMON = new NumericExceptionSubItem("119", "EXISTUNAUDITTEDDEPMON");
    public static final NumericExceptionSubItem EXISTAUDITTEDDEPMON = new NumericExceptionSubItem("120", "EXISTAUDITTEDDEPMON");
    public static final NumericExceptionSubItem EXISTDUPNUM = new NumericExceptionSubItem("121", "EXISTDUPNUM");

    /**
     * construct function
     * @param NumericExceptionSubItem info
     * @param Throwable cause
     * @param Object[] params
     */
    public FDCBudgetAcctException(NumericExceptionSubItem info, Throwable cause, Object[] params)
    {
        super(info, cause, params);
    }

    /**
     * construct function
     * @param NumericExceptionSubItem info,Object[] params
     */
    public FDCBudgetAcctException(NumericExceptionSubItem info, Object[] params)
    {
        this(info, null, params);
    }

    /**
     * construct function
     * @param NumericExceptionSubItem info,Throwable cause
     */
    public FDCBudgetAcctException(NumericExceptionSubItem info, Throwable cause)
    {
        this(info, cause, null);
    }

    /**
     * construct function
     * @param NumericExceptionSubItem info
     */
    public FDCBudgetAcctException(NumericExceptionSubItem info)
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