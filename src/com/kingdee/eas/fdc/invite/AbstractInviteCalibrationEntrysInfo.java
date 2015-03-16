package com.kingdee.eas.fdc.invite;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractInviteCalibrationEntrysInfo extends com.kingdee.eas.fdc.invite.BaseInviteEntryInfo implements Serializable 
{
    public AbstractInviteCalibrationEntrysInfo()
    {
        this("id");
    }
    protected AbstractInviteCalibrationEntrysInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 定标会议分录 's null property 
     */
    public com.kingdee.eas.fdc.invite.InviteCalibrationInfo getParent()
    {
        return (com.kingdee.eas.fdc.invite.InviteCalibrationInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.invite.InviteCalibrationInfo item)
    {
        put("parent", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("5E740D1E");
    }
}