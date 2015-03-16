package com.kingdee.eas.fdc.invite.supplier;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractFDCSplKeepContractAuditBillInfo extends com.kingdee.eas.fdc.invite.supplier.FDCSplAuditBaseBillInfo implements Serializable 
{
    public AbstractFDCSplKeepContractAuditBillInfo()
    {
        this("id");
    }
    protected AbstractFDCSplKeepContractAuditBillInfo(String pkField)
    {
        super(pkField);
        put("auditBill", new com.kingdee.eas.fdc.invite.supplier.FDCSplKeepAuditEntyCollection());
        put("auditValue", new com.kingdee.eas.fdc.invite.supplier.FDCSplKeepAuditIndexValueCollection());
    }
    /**
     * Object: 履约评估 's 工程项目 property 
     */
    public com.kingdee.eas.fdc.basedata.CurProjectInfo getProject()
    {
        return (com.kingdee.eas.fdc.basedata.CurProjectInfo)get("project");
    }
    public void setProject(com.kingdee.eas.fdc.basedata.CurProjectInfo item)
    {
        put("project", item);
    }
    /**
     * Object: 履约评估 's 合同 property 
     */
    public com.kingdee.eas.fdc.contract.ContractBillInfo getContract()
    {
        return (com.kingdee.eas.fdc.contract.ContractBillInfo)get("contract");
    }
    public void setContract(com.kingdee.eas.fdc.contract.ContractBillInfo item)
    {
        put("contract", item);
    }
    /**
     * Object: 履约评估 's 评审等级 property 
     */
    public com.kingdee.eas.fdc.invite.supplier.FDCSplKeepAuditEntyCollection getAuditBill()
    {
        return (com.kingdee.eas.fdc.invite.supplier.FDCSplKeepAuditEntyCollection)get("auditBill");
    }
    /**
     * Object: 履约评估 's 评审模板 property 
     */
    public com.kingdee.eas.fdc.invite.supplier.FDCSplKeepAuditIndexValueCollection getAuditValue()
    {
        return (com.kingdee.eas.fdc.invite.supplier.FDCSplKeepAuditIndexValueCollection)get("auditValue");
    }
    /**
     * Object:履约评估's 是否竣工评估property 
     */
    public boolean isIsCompleteAudit()
    {
        return getBoolean("isCompleteAudit");
    }
    public void setIsCompleteAudit(boolean item)
    {
        setBoolean("isCompleteAudit", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("FC287569");
    }
}