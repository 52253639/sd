/**
 * output package name
 */
package com.kingdee.eas.fdc.schedule;

import com.kingdee.util.NumericExceptionSubItem;
import com.kingdee.eas.common.EASBizException;

/**
 * output class name
 */
public class ScheduleException extends EASBizException
{
    private static final String MAINCODE = "21";

    public static final NumericExceptionSubItem NONUMLEVEL = new NumericExceptionSubItem("000", "NONUMLEVEL");
    public static final NumericExceptionSubItem NUMLEVELISZERO = new NumericExceptionSubItem("001", "NUMLEVELISZERO");
    public static final NumericExceptionSubItem NUMLEVELTOBIG = new NumericExceptionSubItem("002", "NUMLEVELTOBIG");
    public static final NumericExceptionSubItem NOENABLEDCODERULE = new NumericExceptionSubItem("003", "NOENABLEDCODERULE");
    public static final NumericExceptionSubItem CANNTOPERATION = new NumericExceptionSubItem("004", "CANNTOPERATION");
    public static final NumericExceptionSubItem WITHMSG = new NumericExceptionSubItem("005", "WITHMSG");

    /**
     * construct function
     * @param NumericExceptionSubItem info
     * @param Throwable cause
     * @param Object[] params
     */
    public ScheduleException(NumericExceptionSubItem info, Throwable cause, Object[] params)
    {
        super(info, cause, params);
    }

    /**
     * construct function
     * @param NumericExceptionSubItem info,Object[] params
     */
    public ScheduleException(NumericExceptionSubItem info, Object[] params)
    {
        this(info, null, params);
    }

    /**
     * construct function
     * @param NumericExceptionSubItem info,Throwable cause
     */
    public ScheduleException(NumericExceptionSubItem info, Throwable cause)
    {
        this(info, cause, null);
    }

    /**
     * construct function
     * @param NumericExceptionSubItem info
     */
    public ScheduleException(NumericExceptionSubItem info)
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