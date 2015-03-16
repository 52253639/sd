package com.kingdee.eas.fdc.invite;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractInviteCalibrationInfo extends com.kingdee.eas.fdc.invite.BaseInviteInfo implements Serializable 
{
    public AbstractInviteCalibrationInfo()
    {
        this("id");
    }
    protected AbstractInviteCalibrationInfo(String pkField)
    {
        super(pkField);
        put("entry", new com.kingdee.eas.fdc.invite.InviteCalibrationEntrysCollection());
    }
    /**
     * Object: 定标会议 's null property 
     */
    public com.kingdee.eas.fdc.invite.InviteCalibrationEntrysCollection getEntry()
    {
        return (com.kingdee.eas.fdc.invite.InviteCalibrationEntrysCollection)get("entry");
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("E9172C7D");
    }
}