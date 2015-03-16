/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.supplier;

import com.kingdee.util.NumericExceptionSubItem;
import com.kingdee.eas.ma.crbg.CREasBizException;

/**
 * output class name
 */
public class SupplierException extends CREasBizException
{
    private static final String MAINCODE = "19";

    public static final NumericExceptionSubItem BIZDATEEXCEPTION = new NumericExceptionSubItem("000", "BizDateException");
    public static final NumericExceptionSubItem ISEVALULATIONEXCEPTION = new NumericExceptionSubItem("001", "isEvalulationException");
    public static final NumericExceptionSubItem ISGRADEEXCEPTION = new NumericExceptionSubItem("002", "isGradeException");
    public static final NumericExceptionSubItem CONTRACTEXCEPTION = new NumericExceptionSubItem("003", "contractException");
    public static final NumericExceptionSubItem SUPPLIERTYPEEXCEPTION = new NumericExceptionSubItem("004", "supplierTypeException");
    public static final NumericExceptionSubItem GRADE_GRADENAMEEXCEPTION = new NumericExceptionSubItem("005", "grade_gradeNameException");
    public static final NumericExceptionSubItem GRADE_ISGRADEEXCEPTION = new NumericExceptionSubItem("006", "grade_isGradeException");
    public static final NumericExceptionSubItem GRADE_DELETEEXCEPTION = new NumericExceptionSubItem("007", "grade_deleteException");
    public static final NumericExceptionSubItem PRMTSUPPLIERTYPEEXCEPTION = new NumericExceptionSubItem("008", "prmtSupplierTypeException");
    public static final NumericExceptionSubItem UNAUDITEXCEPTION = new NumericExceptionSubItem("009", "unauditException");

    /**
     * construct function
     * @param NumericExceptionSubItem info
     * @param Throwable cause
     * @param Object[] params
     */
    public SupplierException(NumericExceptionSubItem info, Throwable cause, Object[] params)
    {
        super(info, cause, params);
    }

    /**
     * construct function
     * @param NumericExceptionSubItem info,Object[] params
     */
    public SupplierException(NumericExceptionSubItem info, Object[] params)
    {
        this(info, null, params);
    }

    /**
     * construct function
     * @param NumericExceptionSubItem info,Throwable cause
     */
    public SupplierException(NumericExceptionSubItem info, Throwable cause)
    {
        this(info, cause, null);
    }

    /**
     * construct function
     * @param NumericExceptionSubItem info
     */
    public SupplierException(NumericExceptionSubItem info)
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