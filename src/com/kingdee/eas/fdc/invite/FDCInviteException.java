/**
 * output package name
 */
package com.kingdee.eas.fdc.invite;

import com.kingdee.util.NumericExceptionSubItem;
import com.kingdee.eas.common.EASBizException;

/**
 * output class name
 */
public class FDCInviteException extends EASBizException
{
    private static final String MAINCODE = "18";

    public static final NumericExceptionSubItem INTITY_TYPE_ISNULL = new NumericExceptionSubItem("001", "INTITY_TYPE_ISNULL");
    public static final NumericExceptionSubItem UNIT_ISNULL = new NumericExceptionSubItem("002", "UNIT_ISNULL");
    public static final NumericExceptionSubItem DESCRIPTION_IS_OVERFLOW = new NumericExceptionSubItem("003", "DESCRIPTION_IS_OVERFLOW");
    public static final NumericExceptionSubItem NAME_IS_OVER = new NumericExceptionSubItem("004", "NAME_IS_OVER");
    public static final NumericExceptionSubItem NUMBER_IS_OVER_IN_ONE_ORG = new NumericExceptionSubItem("005", "NUMBER_IS_OVER_IN_ONE_ORG");
    public static final NumericExceptionSubItem NAME_IS_OVER_IN_ONE_ORG = new NumericExceptionSubItem("006", "NAME_IS_OVER_IN_ONE_ORG");
    public static final NumericExceptionSubItem SUPPLIER_IS_OVER = new NumericExceptionSubItem("007", "SUPPLIER_IS_OVER");
    public static final NumericExceptionSubItem HEAD_NAME_OVER = new NumericExceptionSubItem("008", "HEAD_NAME_OVER");
    public static final NumericExceptionSubItem HEADTYPE_NULL = new NumericExceptionSubItem("009", "HEADTYPE_NULL");
    public static final NumericExceptionSubItem EXITONDOWNORG = new NumericExceptionSubItem("010", "EXITONDOWNORG");
    public static final NumericExceptionSubItem HAVE_SAME_ITEM_UPORG = new NumericExceptionSubItem("011", "HAVE_SAME_ITEM_UPORG");
    public static final NumericExceptionSubItem HAVE_SAME_ITEM_CURORG = new NumericExceptionSubItem("012", "HAVE_SAME_ITEM_CURORG");
    public static final NumericExceptionSubItem LISTITEM_UNIT_NULL = new NumericExceptionSubItem("013", "LISTITEM_UNIT_NULL");
    public static final NumericExceptionSubItem HAVE_SAME_ITEM_UPORDOWNORG = new NumericExceptionSubItem("014", "HAVE_SAME_ITEM_UPORDOWNORG");
    public static final NumericExceptionSubItem HAVE_SAME_TEMPLET_NUM_INORG = new NumericExceptionSubItem("015", "HAVE_SAME_TEMPLET_NUM_INORG");
    public static final NumericExceptionSubItem HAVE_SAME_TEMPLET_NAME_INORG = new NumericExceptionSubItem("016", "HAVE_SAME_TEMPLET_NAME_INORG");
    public static final NumericExceptionSubItem INVITEMODE_CANNOT_DELETE = new NumericExceptionSubItem("017", "INVITEMODE_CANNOT_DELETE");
    public static final NumericExceptionSubItem CANNOTREVERSIONFINAL = new NumericExceptionSubItem("018", "CANNOTREVERSIONFINAL");
    public static final NumericExceptionSubItem ALREADY_DO_MERGER = new NumericExceptionSubItem("019", "ALREADY_DO_Merger");
    public static final NumericExceptionSubItem INQUIRY_SUPPLIER = new NumericExceptionSubItem("020", "INQUIRY_SUPPLIER");
    public static final NumericExceptionSubItem ALREADY_SIGN_CONTRACT = new NumericExceptionSubItem("021", "ALREADY_SIGN_CONTRACT");
    public static final NumericExceptionSubItem CANNOT_UNAUDIT = new NumericExceptionSubItem("022", "CANNOT_UNAUDIT");
    public static final NumericExceptionSubItem EXISTTENDERACPTRESULT = new NumericExceptionSubItem("023", "ExistTenderAcptResult");

    /**
     * construct function
     * @param NumericExceptionSubItem info
     * @param Throwable cause
     * @param Object[] params
     */
    public FDCInviteException(NumericExceptionSubItem info, Throwable cause, Object[] params)
    {
        super(info, cause, params);
    }

    /**
     * construct function
     * @param NumericExceptionSubItem info,Object[] params
     */
    public FDCInviteException(NumericExceptionSubItem info, Object[] params)
    {
        this(info, null, params);
    }

    /**
     * construct function
     * @param NumericExceptionSubItem info,Throwable cause
     */
    public FDCInviteException(NumericExceptionSubItem info, Throwable cause)
    {
        this(info, cause, null);
    }

    /**
     * construct function
     * @param NumericExceptionSubItem info
     */
    public FDCInviteException(NumericExceptionSubItem info)
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