package com.kingdee.eas.fdc.invite;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractInviteDocumentsInfo extends com.kingdee.eas.fdc.invite.BaseInviteInfo implements Serializable 
{
    public AbstractInviteDocumentsInfo()
    {
        this("id");
    }
    protected AbstractInviteDocumentsInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 招标文件 's 标准招标文件 property 
     */
    public com.kingdee.eas.fdc.invite.InviteBaseFileInfo getInviteBaseFile()
    {
        return (com.kingdee.eas.fdc.invite.InviteBaseFileInfo)get("inviteBaseFile");
    }
    public void setInviteBaseFile(com.kingdee.eas.fdc.invite.InviteBaseFileInfo item)
    {
        put("inviteBaseFile", item);
    }
    /**
     * Object:招标文件's 补充协议关联业务IDproperty 
     */
    public String getAgreementID()
    {
        return getString("agreementID");
    }
    public void setAgreementID(String item)
    {
        setString("agreementID", item);
    }
    /**
     * Object: 招标文件 's 标准合同范本 property 
     */
    public com.kingdee.eas.fdc.contract.ContractModelInfo getContractModel()
    {
        return (com.kingdee.eas.fdc.contract.ContractModelInfo)get("contractModel");
    }
    public void setContractModel(com.kingdee.eas.fdc.contract.ContractModelInfo item)
    {
        put("contractModel", item);
    }
    /**
     * Object:招标文件's 招标范围property 
     */
    public String getScope()
    {
        return getString("scope");
    }
    public void setScope(String item)
    {
        setString("scope", item);
    }
    /**
     * Object:招标文件's 计量规则property 
     */
    public String getRule()
    {
        return getString("rule");
    }
    public void setRule(String item)
    {
        setString("rule", item);
    }
    /**
     * Object:招标文件's 工程量清单property 
     */
    public String getList()
    {
        return getString("list");
    }
    public void setList(String item)
    {
        setString("list", item);
    }
    /**
     * Object:招标文件's 付款方式property 
     */
    public String getPayMethod()
    {
        return getString("payMethod");
    }
    public void setPayMethod(String item)
    {
        setString("payMethod", item);
    }
    /**
     * Object:招标文件's 主材调差公式property 
     */
    public String getFormula()
    {
        return getString("formula");
    }
    public void setFormula(String item)
    {
        setString("formula", item);
    }
    /**
     * Object:招标文件's 结算方式property 
     */
    public String getSettle()
    {
        return getString("settle");
    }
    public void setSettle(String item)
    {
        setString("settle", item);
    }
    /**
     * Object:招标文件's 与标准文本不一致处property 
     */
    public String getDiff()
    {
        return getString("diff");
    }
    public void setDiff(String item)
    {
        setString("diff", item);
    }
    /**
     * Object:招标文件's 其他property 
     */
    public String getOther()
    {
        return getString("other");
    }
    public void setOther(String item)
    {
        setString("other", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("76766E0B");
    }
}