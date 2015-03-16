/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.util.NumericExceptionSubItem;
import com.kingdee.eas.common.EASBizException;

/**
 * output class name
 */
public class SellHouseException extends EASBizException
{
    private static final String MAINCODE = "89";

    public static final NumericExceptionSubItem SAMEBANK = new NumericExceptionSubItem("000", "SAMEBANK");
    public static final NumericExceptionSubItem COMPENSATE = new NumericExceptionSubItem("001", "COMPENSATE");
    public static final NumericExceptionSubItem NOPRICE = new NumericExceptionSubItem("002", "NOPRICE");
    public static final NumericExceptionSubItem NOPAYTYPE = new NumericExceptionSubItem("003", "NOPAYTYPE");
    public static final NumericExceptionSubItem USEDBYSHEPAYTYPE = new NumericExceptionSubItem("004", "USEDBYSHEPAYTYPE");
    public static final NumericExceptionSubItem USEDBYCOMPENSATE = new NumericExceptionSubItem("005", "USEDBYCOMPENSATE");
    public static final NumericExceptionSubItem STATEWRONG = new NumericExceptionSubItem("006", "STATEWRONG");
    public static final NumericExceptionSubItem NOCUSTOMERCLASSIFY = new NumericExceptionSubItem("007", "NOCUSTOMERCLASSIFY");
    public static final NumericExceptionSubItem ROOMAREANULL = new NumericExceptionSubItem("008", "ROOMAREANULL");
    public static final NumericExceptionSubItem BUILDINGAREANULL = new NumericExceptionSubItem("009", "BUILDINGAREANULL");
    public static final NumericExceptionSubItem BILLISNULL = new NumericExceptionSubItem("010", "BILLISNULL");

    /**
     * construct function
     * @param NumericExceptionSubItem info
     * @param Throwable cause
     * @param Object[] params
     */
    public SellHouseException(NumericExceptionSubItem info, Throwable cause, Object[] params)
    {
        super(info, cause, params);
    }

    /**
     * construct function
     * @param NumericExceptionSubItem info,Object[] params
     */
    public SellHouseException(NumericExceptionSubItem info, Object[] params)
    {
        this(info, null, params);
    }

    /**
     * construct function
     * @param NumericExceptionSubItem info,Throwable cause
     */
    public SellHouseException(NumericExceptionSubItem info, Throwable cause)
    {
        this(info, cause, null);
    }

    /**
     * construct function
     * @param NumericExceptionSubItem info
     */
    public SellHouseException(NumericExceptionSubItem info)
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