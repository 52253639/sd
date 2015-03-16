package com.kingdee.eas.fdc.invite;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractAttachReportInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractAttachReportInfo()
    {
        this("id");
    }
    protected AbstractAttachReportInfo(String pkField)
    {
        super(pkField);
        put("entrys", new com.kingdee.eas.fdc.invite.AttachReportEntryCollection());
    }
    /**
     * Object: 其他附件报表 's 分录 property 
     */
    public com.kingdee.eas.fdc.invite.AttachReportEntryCollection getEntrys()
    {
        return (com.kingdee.eas.fdc.invite.AttachReportEntryCollection)get("entrys");
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("838194FD");
    }
}