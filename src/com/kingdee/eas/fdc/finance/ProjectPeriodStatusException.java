/**
 * output package name
 */
package com.kingdee.eas.fdc.finance;

import com.kingdee.util.NumericExceptionSubItem;
import com.kingdee.eas.common.EASBizException;

/**
 * output class name
 */
public class ProjectPeriodStatusException extends EASBizException
{
    private static final String MAINCODE = "78";

    public static final NumericExceptionSubItem BOOKEDDATENOTPERIOD = new NumericExceptionSubItem("001", "BOOKEDDATENOTPERIOD");
    public static final NumericExceptionSubItem AUDIT_CUR_PERIOD = new NumericExceptionSubItem("002", "AUDIT_CUR_PERIOD");
    public static final NumericExceptionSubItem NOT_SET_START = new NumericExceptionSubItem("003", "NOT_SET_START");
    public static final NumericExceptionSubItem NOT_COM_START = new NumericExceptionSubItem("004", "NOT_COM_START");
    public static final NumericExceptionSubItem CNT_DELPRO = new NumericExceptionSubItem("005", "CNT_DELPRO");
    public static final NumericExceptionSubItem CNT_UNINITPROBYCOS = new NumericExceptionSubItem("006", "CNT_UNINITPROBYCOS");
    public static final NumericExceptionSubItem CNT_UNINITPROBYCON = new NumericExceptionSubItem("007", "CNT_UNINITPROBYCON");
    public static final NumericExceptionSubItem ISNOT_INIT = new NumericExceptionSubItem("008", "ISNOT_INIT");
    public static final NumericExceptionSubItem CLOPRO_HASEND = new NumericExceptionSubItem("009", "CLOPRO_HASEND");
    public static final NumericExceptionSubItem CLOPRO_UNINIT = new NumericExceptionSubItem("010", "CLOPRO_UNINIT");
    public static final NumericExceptionSubItem CLOPRO_HASCLOSED = new NumericExceptionSubItem("000", "CLOPRO_HASCLOSED");
    public static final NumericExceptionSubItem CLOPRO_HASCONTR = new NumericExceptionSubItem("011", "CLOPRO_HASCONTR");
    public static final NumericExceptionSubItem CLOPRO_NOTEND = new NumericExceptionSubItem("012", "CLOPRO_NOTEND");
    public static final NumericExceptionSubItem CLOPRO_BEFORSY = new NumericExceptionSubItem("013", "CLOPRO_BEFORSY");
    public static final NumericExceptionSubItem NT_FREEZECOST = new NumericExceptionSubItem("014", "NT_FREEZECOST");
    public static final NumericExceptionSubItem NT_FREEZEFINA = new NumericExceptionSubItem("015", "NT_FREEZEFINA");
    public static final NumericExceptionSubItem NT_LAST = new NumericExceptionSubItem("016", "NT_LAST");
    public static final NumericExceptionSubItem NT_NEXTCOST = new NumericExceptionSubItem("017", "NT_NEXTCOST");
    public static final NumericExceptionSubItem NT_NEXTFINA = new NumericExceptionSubItem("018", "NT_NEXTFINA");
    public static final NumericExceptionSubItem NT_LASTCOST = new NumericExceptionSubItem("019", "NT_LASTCOST");
    public static final NumericExceptionSubItem NT_LASTFINA = new NumericExceptionSubItem("020", "NT_LASTFINA");
    public static final NumericExceptionSubItem NT_END = new NumericExceptionSubItem("021", "NT_END");
    public static final NumericExceptionSubItem CHK_CONTRACT = new NumericExceptionSubItem("022", "CHK_CONTRACT");
    public static final NumericExceptionSubItem CHK_CONTRACTWITHOUT = new NumericExceptionSubItem("023", "CHK_CONTRACTWITHOUT");
    public static final NumericExceptionSubItem CLOPRO_HASCONTRNO = new NumericExceptionSubItem("024", "CLOPRO_HASCONTRNO");
    public static final NumericExceptionSubItem EX_HASCLOSED = new NumericExceptionSubItem("025", "EX_HASCLOSED");
    public static final NumericExceptionSubItem CNT_UNINITPROBYCONNO = new NumericExceptionSubItem("026", "CNT_UNINITPROBYCONNO");
    public static final NumericExceptionSubItem CNT_UNINITPROBYCONAU = new NumericExceptionSubItem("027", "CNT_UNINITPROBYCONAU");
    public static final NumericExceptionSubItem UN_START = new NumericExceptionSubItem("028", "UN_START");
    public static final NumericExceptionSubItem NOTEXTPERIOD = new NumericExceptionSubItem("029", "NOTEXTPERIOD");
    public static final NumericExceptionSubItem HASVOUCHERD = new NumericExceptionSubItem("030", "HASVOUCHERD");
    public static final NumericExceptionSubItem HASCLOSED = new NumericExceptionSubItem("031", "HASCLOSED");
    public static final NumericExceptionSubItem NOTSETTMORE = new NumericExceptionSubItem("032", "NOTSETTMORE");
    public static final NumericExceptionSubItem NOTFINACIALBYSEETTMORE = new NumericExceptionSubItem("033", "NOTFINACIALBYSEETTMORE");
    public static final NumericExceptionSubItem CBTNOTSETTMORE = new NumericExceptionSubItem("034", "CBTNOTSETTMORE");
    public static final NumericExceptionSubItem EXISTCONTRACT = new NumericExceptionSubItem("035", "EXISTCONTRACT");
    public static final NumericExceptionSubItem CANTSTARTBECAUSEMORESETTLE = new NumericExceptionSubItem("036", "CANTSTARTBECAUSEMORESETTLE");
    public static final NumericExceptionSubItem PERIOD_CNT_EMPTY = new NumericExceptionSubItem("037", "PERIOD_CNT_EMPTY");

    /**
     * construct function
     * @param NumericExceptionSubItem info
     * @param Throwable cause
     * @param Object[] params
     */
    public ProjectPeriodStatusException(NumericExceptionSubItem info, Throwable cause, Object[] params)
    {
        super(info, cause, params);
    }

    /**
     * construct function
     * @param NumericExceptionSubItem info,Object[] params
     */
    public ProjectPeriodStatusException(NumericExceptionSubItem info, Object[] params)
    {
        this(info, null, params);
    }

    /**
     * construct function
     * @param NumericExceptionSubItem info,Throwable cause
     */
    public ProjectPeriodStatusException(NumericExceptionSubItem info, Throwable cause)
    {
        this(info, cause, null);
    }

    /**
     * construct function
     * @param NumericExceptionSubItem info
     */
    public ProjectPeriodStatusException(NumericExceptionSubItem info)
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