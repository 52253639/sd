/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.supplier;

import com.kingdee.util.NumericExceptionSubItem;
import com.kingdee.eas.common.EASBizException;

/**
 * output class name
 */
public class ControlBeanException extends EASBizException
{
    private static final String MAINCODE = "99";

    public static final NumericExceptionSubItem GRADE_DELETEEXCEPTION = new NumericExceptionSubItem("000", "grade_deleteException");
    public static final NumericExceptionSubItem AREA_DELETEEXCEPETION = new NumericExceptionSubItem("001", "area_deleteExcepetion");
    public static final NumericExceptionSubItem SPL_SERVICETYPEEXCEPTION = new NumericExceptionSubItem("002", "spl_serviceTypeException");
    public static final NumericExceptionSubItem SERVICEEXCEPTION = new NumericExceptionSubItem("003", "serviceException");

    /**
     * construct function
     * @param NumericExceptionSubItem info
     * @param Throwable cause
     * @param Object[] params
     */
    public ControlBeanException(NumericExceptionSubItem info, Throwable cause, Object[] params)
    {
        super(info, cause, params);
    }

    /**
     * construct function
     * @param NumericExceptionSubItem info,Object[] params
     */
    public ControlBeanException(NumericExceptionSubItem info, Object[] params)
    {
        this(info, null, params);
    }

    /**
     * construct function
     * @param NumericExceptionSubItem info,Throwable cause
     */
    public ControlBeanException(NumericExceptionSubItem info, Throwable cause)
    {
        this(info, cause, null);
    }

    /**
     * construct function
     * @param NumericExceptionSubItem info
     */
    public ControlBeanException(NumericExceptionSubItem info)
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