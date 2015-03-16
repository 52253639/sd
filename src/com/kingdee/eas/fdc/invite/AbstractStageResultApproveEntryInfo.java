package com.kingdee.eas.fdc.invite;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractStageResultApproveEntryInfo extends com.kingdee.eas.fdc.invite.BaseInviteEntryInfo implements Serializable 
{
    public AbstractStageResultApproveEntryInfo()
    {
        this("id");
    }
    protected AbstractStageResultApproveEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 阶段性成果审批分录 's 阶段性成果审批 property 
     */
    public com.kingdee.eas.fdc.invite.StageResultApproveInfo getParent()
    {
        return (com.kingdee.eas.fdc.invite.StageResultApproveInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.invite.StageResultApproveInfo item)
    {
        put("parent", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("976F25DC");
    }
}