package com.kingdee.eas.fdc.invite;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractInvitePreSplitInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractInvitePreSplitInfo()
    {
        this("id");
    }
    protected AbstractInvitePreSplitInfo(String pkField)
    {
        super(pkField);
        put("entry", new com.kingdee.eas.fdc.invite.InvitePreSplitEntryCollection());
    }
    /**
     * Object: 招标预拆分 's 招标立项 property 
     */
    public com.kingdee.eas.fdc.invite.InviteProjectInfo getInviteProject()
    {
        return (com.kingdee.eas.fdc.invite.InviteProjectInfo)get("inviteProject");
    }
    public void setInviteProject(com.kingdee.eas.fdc.invite.InviteProjectInfo item)
    {
        put("inviteProject", item);
    }
    /**
     * Object:招标预拆分's 预拆分金额property 
     */
    public java.math.BigDecimal getPreSplitAmount()
    {
        return getBigDecimal("preSplitAmount");
    }
    public void setPreSplitAmount(java.math.BigDecimal item)
    {
        setBigDecimal("preSplitAmount", item);
    }
    /**
     * Object: 招标预拆分 's 分录 property 
     */
    public com.kingdee.eas.fdc.invite.InvitePreSplitEntryCollection getEntry()
    {
        return (com.kingdee.eas.fdc.invite.InvitePreSplitEntryCollection)get("entry");
    }
    /**
     * Object:招标预拆分's 合同是否拆分property 
     */
    public boolean isIsContractSplit()
    {
        return getBoolean("isContractSplit");
    }
    public void setIsContractSplit(boolean item)
    {
        setBoolean("isContractSplit", item);
    }
    /**
     * Object:招标预拆分's 是否关联合同property 
     */
    public boolean isIsAssContract()
    {
        return getBoolean("isAssContract");
    }
    public void setIsAssContract(boolean item)
    {
        setBoolean("isAssContract", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("1F865484");
    }
}