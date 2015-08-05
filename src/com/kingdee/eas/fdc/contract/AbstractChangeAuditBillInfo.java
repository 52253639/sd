package com.kingdee.eas.fdc.contract;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractChangeAuditBillInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractChangeAuditBillInfo()
    {
        this("id");
    }
    protected AbstractChangeAuditBillInfo(String pkField)
    {
        super(pkField);
        put("suppEntry", new com.kingdee.eas.fdc.contract.ChangeSupplierEntryCollection());
        put("mutiSpecialtyType", new com.kingdee.eas.fdc.contract.SpecialtyTypeEntryCollection());
        put("entrys", new com.kingdee.eas.fdc.contract.ChangeAuditEntryCollection());
    }
    /**
     * Object: 变更审批单 's 变更内容分录 property 
     */
    public com.kingdee.eas.fdc.contract.ChangeAuditEntryCollection getEntrys()
    {
        return (com.kingdee.eas.fdc.contract.ChangeAuditEntryCollection)get("entrys");
    }
    /**
     * Object: 变更审批单 's 工程项目 property 
     */
    public com.kingdee.eas.fdc.basedata.CurProjectInfo getCurProject()
    {
        return (com.kingdee.eas.fdc.basedata.CurProjectInfo)get("curProject");
    }
    public void setCurProject(com.kingdee.eas.fdc.basedata.CurProjectInfo item)
    {
        put("curProject", item);
    }
    /**
     * Object: 变更审批单 's 变更类型 property 
     */
    public com.kingdee.eas.fdc.basedata.ChangeTypeInfo getAuditType()
    {
        return (com.kingdee.eas.fdc.basedata.ChangeTypeInfo)get("auditType");
    }
    public void setAuditType(com.kingdee.eas.fdc.basedata.ChangeTypeInfo item)
    {
        put("auditType", item);
    }
    /**
     * Object: 变更审批单 's 变更原因 property 
     */
    public com.kingdee.eas.fdc.basedata.ChangeReasonInfo getChangeReason()
    {
        return (com.kingdee.eas.fdc.basedata.ChangeReasonInfo)get("changeReason");
    }
    public void setChangeReason(com.kingdee.eas.fdc.basedata.ChangeReasonInfo item)
    {
        put("changeReason", item);
    }
    /**
     * Object:变更审批单's 状态property 
     */
    public com.kingdee.eas.fdc.contract.ChangeBillStateEnum getChangeState()
    {
        return com.kingdee.eas.fdc.contract.ChangeBillStateEnum.getEnum(getString("changeState"));
    }
    public void setChangeState(com.kingdee.eas.fdc.contract.ChangeBillStateEnum item)
    {
		if (item != null) {
        setString("changeState", item.getValue());
		}
    }
    /**
     * Object:变更审批单's 紧急程度property 
     */
    public com.kingdee.eas.fdc.contract.ChangeUrgentDegreeEnum getUrgentDegree()
    {
        return com.kingdee.eas.fdc.contract.ChangeUrgentDegreeEnum.getEnum(getString("urgentDegree"));
    }
    public void setUrgentDegree(com.kingdee.eas.fdc.contract.ChangeUrgentDegreeEnum item)
    {
		if (item != null) {
        setString("urgentDegree", item.getValue());
		}
    }
    /**
     * Object: 变更审批单 's 变更审批单下发单位分录 property 
     */
    public com.kingdee.eas.fdc.contract.ChangeSupplierEntryCollection getSuppEntry()
    {
        return (com.kingdee.eas.fdc.contract.ChangeSupplierEntryCollection)get("suppEntry");
    }
    /**
     * Object: 变更审批单 's 承包类型 property 
     */
    public com.kingdee.eas.fdc.basedata.JobTypeInfo getJobType()
    {
        return (com.kingdee.eas.fdc.basedata.JobTypeInfo)get("jobType");
    }
    public void setJobType(com.kingdee.eas.fdc.basedata.JobTypeInfo item)
    {
        put("jobType", item);
    }
    /**
     * Object: 变更审批单 's 专业类型（废弃，需求需要多选） property 
     */
    public com.kingdee.eas.fdc.basedata.SpecialtyTypeInfo getSpecialtyType()
    {
        return (com.kingdee.eas.fdc.basedata.SpecialtyTypeInfo)get("specialtyType");
    }
    public void setSpecialtyType(com.kingdee.eas.fdc.basedata.SpecialtyTypeInfo item)
    {
        put("specialtyType", item);
    }
    /**
     * Object:变更审批单's 变更主题property 
     */
    public String getChangeSubject()
    {
        return getString("changeSubject");
    }
    public void setChangeSubject(String item)
    {
        setString("changeSubject", item);
    }
    /**
     * Object:变更审批单's 附图情况property 
     */
    public com.kingdee.eas.fdc.contract.GraphCountEnum getGraphCount()
    {
        return com.kingdee.eas.fdc.contract.GraphCountEnum.getEnum(getString("graphCount"));
    }
    public void setGraphCount(com.kingdee.eas.fdc.contract.GraphCountEnum item)
    {
		if (item != null) {
        setString("graphCount", item.getValue());
		}
    }
    /**
     * Object:变更审批单's 下发单位个数property 
     */
    public int getSuppCount()
    {
        return getInt("suppCount");
    }
    public void setSuppCount(int item)
    {
        setInt("suppCount", item);
    }
    /**
     * Object:变更审批单's 是否提前下发property 
     */
    public boolean isIsRegister()
    {
        return getBoolean("isRegister");
    }
    public void setIsRegister(boolean item)
    {
        setBoolean("isRegister", item);
    }
    /**
     * Object:变更审批单's 成本测算金额汇总property 
     */
    public java.math.BigDecimal getTotalCost()
    {
        return getBigDecimal("totalCost");
    }
    public void setTotalCost(java.math.BigDecimal item)
    {
        setBigDecimal("totalCost", item);
    }
    /**
     * Object:变更审批单's 涉及无效成本的金额property 
     */
    public java.math.BigDecimal getCostNouse()
    {
        return getBigDecimal("costNouse");
    }
    public void setCostNouse(java.math.BigDecimal item)
    {
        setBigDecimal("costNouse", item);
    }
    /**
     * Object:变更审批单's 涉及无效成本原因property 
     */
    public String getReason()
    {
        return getString("reason");
    }
    public void setReason(String item)
    {
        setString("reason", item);
    }
    /**
     * Object:变更审批单's 甲方承担费用property 
     */
    public java.math.BigDecimal getAmountA()
    {
        return getBigDecimal("amountA");
    }
    public void setAmountA(java.math.BigDecimal item)
    {
        setBigDecimal("amountA", item);
    }
    /**
     * Object:变更审批单's 责任单位承担费用property 
     */
    public java.math.BigDecimal getAmountDutySupp()
    {
        return getBigDecimal("amountDutySupp");
    }
    public void setAmountDutySupp(java.math.BigDecimal item)
    {
        setBigDecimal("amountDutySupp", item);
    }
    /**
     * Object: 变更审批单 's 提出部门 property 
     */
    public com.kingdee.eas.basedata.org.AdminOrgUnitInfo getConductDept()
    {
        return (com.kingdee.eas.basedata.org.AdminOrgUnitInfo)get("conductDept");
    }
    public void setConductDept(com.kingdee.eas.basedata.org.AdminOrgUnitInfo item)
    {
        put("conductDept", item);
    }
    /**
     * Object:变更审批单's 提前下发原因property 
     */
    public String getAheadReason()
    {
        return getString("aheadReason");
    }
    public void setAheadReason(String item)
    {
        setString("aheadReason", item);
    }
    /**
     * Object:变更审批单's 批准人property 
     */
    public String getValidator()
    {
        return getString("validator");
    }
    public void setValidator(String item)
    {
        setString("validator", item);
    }
    /**
     * Object:变更审批单's 沟通方式property 
     */
    public String getConnectType()
    {
        return getString("connectType");
    }
    public void setConnectType(String item)
    {
        setString("connectType", item);
    }
    /**
     * Object:变更审批单's 工程项目property 
     */
    public String getCurProjectName()
    {
        return getString("curProjectName");
    }
    public void setCurProjectName(String item)
    {
        setString("curProjectName", item);
    }
    /**
     * Object:变更审批单's 变更类型property 
     */
    public String getAuditTypeName()
    {
        return getString("auditTypeName");
    }
    public void setAuditTypeName(String item)
    {
        setString("auditTypeName", item);
    }
    /**
     * Object:变更审批单's 承包类型property 
     */
    public String getJobTypeName()
    {
        return getString("jobTypeName");
    }
    public void setJobTypeName(String item)
    {
        setString("jobTypeName", item);
    }
    /**
     * Object:变更审批单's 专业类型property 
     */
    public String getSpecialtyTypeName()
    {
        return getString("specialtyTypeName");
    }
    public void setSpecialtyTypeName(String item)
    {
        setString("specialtyTypeName", item);
    }
    /**
     * Object:变更审批单's 是否存在无效成本property 
     */
    public boolean isIsNoUse()
    {
        return getBoolean("isNoUse");
    }
    public void setIsNoUse(boolean item)
    {
        setBoolean("isNoUse", item);
    }
    /**
     * Object: 变更审批单 's 无效成本原因 property 
     */
    public com.kingdee.eas.fdc.basedata.InvalidCostReasonInfo getInvalidCostReason()
    {
        return (com.kingdee.eas.fdc.basedata.InvalidCostReasonInfo)get("invalidCostReason");
    }
    public void setInvalidCostReason(com.kingdee.eas.fdc.basedata.InvalidCostReasonInfo item)
    {
        put("invalidCostReason", item);
    }
    /**
     * Object:变更审批单's 单据来源方式property 
     */
    public com.kingdee.eas.fdc.basedata.SourceTypeEnum getSourceType()
    {
        return com.kingdee.eas.fdc.basedata.SourceTypeEnum.getEnum(getInt("sourceType"));
    }
    public void setSourceType(com.kingdee.eas.fdc.basedata.SourceTypeEnum item)
    {
		if (item != null) {
        setInt("sourceType", item.getValue());
		}
    }
    /**
     * Object:变更审批单's 提出方property 
     */
    public com.kingdee.eas.fdc.contract.OfferEnum getOffer()
    {
        return com.kingdee.eas.fdc.contract.OfferEnum.getEnum(getString("offer"));
    }
    public void setOffer(com.kingdee.eas.fdc.contract.OfferEnum item)
    {
		if (item != null) {
        setString("offer", item.getValue());
		}
    }
    /**
     * Object: 变更审批单 's 施工单位 property 
     */
    public com.kingdee.eas.basedata.master.cssp.SupplierInfo getConstrUnit()
    {
        return (com.kingdee.eas.basedata.master.cssp.SupplierInfo)get("constrUnit");
    }
    public void setConstrUnit(com.kingdee.eas.basedata.master.cssp.SupplierInfo item)
    {
        put("constrUnit", item);
    }
    /**
     * Object: 变更审批单 's 设计单位 property 
     */
    public com.kingdee.eas.basedata.master.cssp.SupplierInfo getDesignUnit()
    {
        return (com.kingdee.eas.basedata.master.cssp.SupplierInfo)get("designUnit");
    }
    public void setDesignUnit(com.kingdee.eas.basedata.master.cssp.SupplierInfo item)
    {
        put("designUnit", item);
    }
    /**
     * Object:变更审批单's 施工部位 property 
     */
    public String getConstrSite()
    {
        return getString("constrSite");
    }
    public void setConstrSite(String item)
    {
        setString("constrSite", item);
    }
    /**
     * Object:变更审批单's 原因说明property 
     */
    public String getReaDesc()
    {
        return getString("reaDesc");
    }
    public void setReaDesc(String item)
    {
        setString("reaDesc", item);
    }
    /**
     * Object: 变更审批单 's 提出单位 property 
     */
    public com.kingdee.eas.basedata.master.cssp.SupplierInfo getConductUnit()
    {
        return (com.kingdee.eas.basedata.master.cssp.SupplierInfo)get("conductUnit");
    }
    public void setConductUnit(com.kingdee.eas.basedata.master.cssp.SupplierInfo item)
    {
        put("conductUnit", item);
    }
    /**
     * Object:变更审批单's 是否重大变更property 
     */
    public boolean isIsImportChange()
    {
        return getBoolean("isImportChange");
    }
    public void setIsImportChange(boolean item)
    {
        setBoolean("isImportChange", item);
    }
    /**
     * Object: 变更审批单 's 专业类型（可以多选的） property 
     */
    public com.kingdee.eas.fdc.contract.SpecialtyTypeEntryCollection getMutiSpecialtyType()
    {
        return (com.kingdee.eas.fdc.contract.SpecialtyTypeEntryCollection)get("mutiSpecialtyType");
    }
    /**
     * Object:变更审批单's 是否增减合同property 
     */
    public boolean isIsChangeContract()
    {
        return getBoolean("isChangeContract");
    }
    public void setIsChangeContract(boolean item)
    {
        setBoolean("isChangeContract", item);
    }
    /**
     * Object:变更审批单's 是否回执单property 
     */
    public boolean isIsReceipts()
    {
        return getBoolean("isReceipts");
    }
    public void setIsReceipts(boolean item)
    {
        setBoolean("isReceipts", item);
    }
    /**
     * Object:变更审批单's 费用承担单位property 
     */
    public String getCostUnit()
    {
        return getString("costUnit");
    }
    public void setCostUnit(String item)
    {
        setString("costUnit", item);
    }
    /**
     * Object:变更审批单's 是否费用审定property 
     */
    public com.kingdee.eas.fdc.contract.app.YesOrNoEnum getIsFee()
    {
        return com.kingdee.eas.fdc.contract.app.YesOrNoEnum.getEnum(getString("isFee"));
    }
    public void setIsFee(com.kingdee.eas.fdc.contract.app.YesOrNoEnum item)
    {
		if (item != null) {
        setString("isFee", item.getValue());
		}
    }
    /**
     * Object: 变更审批单 's 流程类型 property 
     */
    public com.kingdee.eas.fdc.contract.ChangeWFTypeInfo getWfType()
    {
        return (com.kingdee.eas.fdc.contract.ChangeWFTypeInfo)get("wfType");
    }
    public void setWfType(com.kingdee.eas.fdc.contract.ChangeWFTypeInfo item)
    {
        put("wfType", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("70116117");
    }
}