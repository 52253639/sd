/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.programming;

import com.kingdee.util.NumericExceptionSubItem;
import com.kingdee.eas.common.EASBizException;

/**
 * output class name
 */
public class ProgrammingException extends EASBizException
{
    private static final String MAINCODE = "00";

    public static final NumericExceptionSubItem NUMBER_NULL = new NumericExceptionSubItem("000", "Number_Null");
    public static final NumericExceptionSubItem NAME_NULL = new NumericExceptionSubItem("001", "Name_NULL");
    public static final NumericExceptionSubItem NUMBER_REPEAT = new NumericExceptionSubItem("002", "Number_Repeat");
    public static final NumericExceptionSubItem NAMENOTNULL = new NumericExceptionSubItem("003", "NAMENOTNULL");
    public static final NumericExceptionSubItem CHECKDUPLICATED = new NumericExceptionSubItem("004", "CHECKDUPLICATED");

    /**
     * construct function
     * @param NumericExceptionSubItem info
     * @param Throwable cause
     * @param Object[] params
     */
    public ProgrammingException(NumericExceptionSubItem info, Throwable cause, Object[] params)
    {
        super(info, cause, params);
    }

    /**
     * construct function
     * @param NumericExceptionSubItem info,Object[] params
     */
    public ProgrammingException(NumericExceptionSubItem info, Object[] params)
    {
        this(info, null, params);
    }

    /**
     * construct function
     * @param NumericExceptionSubItem info,Throwable cause
     */
    public ProgrammingException(NumericExceptionSubItem info, Throwable cause)
    {
        this(info, cause, null);
    }

    /**
     * construct function
     * @param NumericExceptionSubItem info
     */
    public ProgrammingException(NumericExceptionSubItem info)
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