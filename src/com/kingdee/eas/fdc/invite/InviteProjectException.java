/**
 * output package name
 */
package com.kingdee.eas.fdc.invite;

import com.kingdee.util.NumericExceptionSubItem;
import com.kingdee.eas.common.EASBizException;

/**
 * output class name
 */
public class InviteProjectException extends EASBizException
{
    private static final String MAINCODE = "19";

    public static final NumericExceptionSubItem HASNOTSETTEMPLATE = new NumericExceptionSubItem("000", "HasNotSetTemplate");
    public static final NumericExceptionSubItem LOGINUSERNOTINEXPERTLISTS = new NumericExceptionSubItem("001", "LoginUserNotInExpertLists");
    public static final NumericExceptionSubItem LOGINUSERHASAPPRAISE = new NumericExceptionSubItem("002", "LoginUserHasAppraise");
    public static final NumericExceptionSubItem HASEXPERTAPPRAISECANTUNAUDIT = new NumericExceptionSubItem("003", "HasExpertAppraiseCantUnAudit");
    public static final NumericExceptionSubItem HASSUPPLIERRECORDCANTUNAUDIT = new NumericExceptionSubItem("004", "HasSupplierRecordCantUnAudit");
    public static final NumericExceptionSubItem HASAPPRAISERESULTCANTUNAUDIT = new NumericExceptionSubItem("005", "HasAppraiseResultCantUnAudit");
    public static final NumericExceptionSubItem NOTTHECREATOR = new NumericExceptionSubItem("006", "NotTheCreator");
    public static final NumericExceptionSubItem HASREFERENCEDTEMPLATE = new NumericExceptionSubItem("007", "HasReferencedTemplate");
    public static final NumericExceptionSubItem EXPERTAPPRAISENOTCOMPLETE = new NumericExceptionSubItem("008", "ExpertAppraiseNotComplete");
    public static final NumericExceptionSubItem APPRAISERESULTHASEXIST = new NumericExceptionSubItem("009", "AppraiseResultHasExist");
    public static final NumericExceptionSubItem HASINVITECLARIFY = new NumericExceptionSubItem("010", "HasInviteClarify");
    public static final NumericExceptionSubItem HASNOBIDSUPPLIER = new NumericExceptionSubItem("011", "HasNoBidSupplier");
    public static final NumericExceptionSubItem HASACCEPTANCELETTER = new NumericExceptionSubItem("012", "HasAcceptanceLetter");
    public static final NumericExceptionSubItem HASINVITEPROJECTREFERENCE = new NumericExceptionSubItem("013", "HasInviteProjectReference");
    public static final NumericExceptionSubItem NOAPPLICATIONFILE = new NumericExceptionSubItem("014", "NoApplicationFile");
    public static final NumericExceptionSubItem HASCHILDINVITEPROJECT = new NumericExceptionSubItem("015", "HasChildInviteProject");
    public static final NumericExceptionSubItem CANTADDSUPPLIERRECORD = new NumericExceptionSubItem("016", "CantAddSupplierRecord");
    public static final NumericExceptionSubItem CANTADDEXPERTAPPRAISE = new NumericExceptionSubItem("017", "CantAddExpertAppraise");
    public static final NumericExceptionSubItem CANTADDEXPERTAPPRAISEOUTDATE = new NumericExceptionSubItem("018", "CantAddExpertAppraiseOutDate");
    public static final NumericExceptionSubItem CANSELECTQUOINGPRICE = new NumericExceptionSubItem("019", "CanSelectQuoingPrice");
    public static final NumericExceptionSubItem CANDELETNEWLISTHASFILEMERGE = new NumericExceptionSubItem("020", "CanDeletNewListHasFileMerge");
    public static final NumericExceptionSubItem HAS_EXPERT_APPRAISE_RESULT = new NumericExceptionSubItem("021", "Has_Expert_Appraise_Result");

    /**
     * construct function
     * @param NumericExceptionSubItem info
     * @param Throwable cause
     * @param Object[] params
     */
    public InviteProjectException(NumericExceptionSubItem info, Throwable cause, Object[] params)
    {
        super(info, cause, params);
    }

    /**
     * construct function
     * @param NumericExceptionSubItem info,Object[] params
     */
    public InviteProjectException(NumericExceptionSubItem info, Object[] params)
    {
        this(info, null, params);
    }

    /**
     * construct function
     * @param NumericExceptionSubItem info,Throwable cause
     */
    public InviteProjectException(NumericExceptionSubItem info, Throwable cause)
    {
        this(info, cause, null);
    }

    /**
     * construct function
     * @param NumericExceptionSubItem info
     */
    public InviteProjectException(NumericExceptionSubItem info)
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