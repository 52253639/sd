/**
 * output package name
 */
package com.kingdee.eas.fdc.aimcost;

import com.kingdee.util.NumericExceptionSubItem;
import com.kingdee.eas.common.EASBizException;

/**
 * output class name
 */
public class CostRptException extends EASBizException
{
    private static final String MAINCODE = "99";

    public static final NumericExceptionSubItem CANNTMONTHSAVE = new NumericExceptionSubItem("000", "canntMonthSave");
    public static final NumericExceptionSubItem CANNTGETPERIOD = new NumericExceptionSubItem("001", "canntGetPeriod");
    public static final NumericExceptionSubItem NOTEXISTCOSTMONTHSAVED = new NumericExceptionSubItem("002", "notExistCostMonthSaved");
    public static final NumericExceptionSubItem HAS_COST_END = new NumericExceptionSubItem("003", "HAS_COST_END");

    /**
     * construct function
     * @param NumericExceptionSubItem info
     * @param Throwable cause
     * @param Object[] params
     */
    public CostRptException(NumericExceptionSubItem info, Throwable cause, Object[] params)
    {
        super(info, cause, params);
    }

    /**
     * construct function
     * @param NumericExceptionSubItem info,Object[] params
     */
    public CostRptException(NumericExceptionSubItem info, Object[] params)
    {
        this(info, null, params);
    }

    /**
     * construct function
     * @param NumericExceptionSubItem info,Throwable cause
     */
    public CostRptException(NumericExceptionSubItem info, Throwable cause)
    {
        this(info, cause, null);
    }

    /**
     * construct function
     * @param NumericExceptionSubItem info
     */
    public CostRptException(NumericExceptionSubItem info)
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