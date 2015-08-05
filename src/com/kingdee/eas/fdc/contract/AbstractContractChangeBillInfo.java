package com.kingdee.eas.fdc.contract;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractContractChangeBillInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractContractChangeBillInfo()
    {
        this("id");
    }
    protected AbstractContractChangeBillInfo(String pkField)
    {
        super(pkField);
        put("newMainSupp", new com.kingdee.eas.fdc.contract.NewMainSupplierEntryCollection());
        put("mutiSpecialtyType", new com.kingdee.eas.fdc.contract.SpecialtyTypeEntryCollection());
        put("entrys", new com.kingdee.eas.fdc.contract.ContractChangeEntryCollection());
    }
    /**
     * Object: 变更指令单 's 变更类型 property 
     */
    public com.kingdee.eas.fdc.basedata.ChangeTypeInfo getChangeType()
    {
        return (com.kingdee.eas.fdc.basedata.ChangeTypeInfo)get("changeType");
    }
    public void setChangeType(com.kingdee.eas.fdc.basedata.ChangeTypeInfo item)
    {
        put("changeType", item);
    }
    /**
     * Object:变更指令单's 变更提出时间property 
     */
    public java.util.Date getConductTime()
    {
        return getDate("conductTime");
    }
    public void setConductTime(java.util.Date item)
    {
        setDate("conductTime", item);
    }
    /**
     * Object: 变更指令单 's 提出部门 property 
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
     * Object: 变更指令单 's 变更原因 property 
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
     * Object:变更指令单's 变更金额性质property 
     */
    public com.kingdee.eas.fdc.contract.ChangeAmtPropEnum getChangeAmtProp()
    {
        return com.kingdee.eas.fdc.contract.ChangeAmtPropEnum.getEnum(getString("changeAmtProp"));
    }
    public void setChangeAmtProp(com.kingdee.eas.fdc.contract.ChangeAmtPropEnum item)
    {
		if (item != null) {
        setString("changeAmtProp", item.getValue());
		}
    }
    /**
     * Object:变更指令单's 变更结算时间property 
     */
    public java.util.Date getSettleTime()
    {
        return getDate("settleTime");
    }
    public void setSettleTime(java.util.Date item)
    {
        setDate("settleTime", item);
    }
    /**
     * Object: 变更指令单 's 预算员 property 
     */
    public com.kingdee.eas.basedata.person.PersonInfo getBudgetPerson()
    {
        return (com.kingdee.eas.basedata.person.PersonInfo)get("budgetPerson");
    }
    public void setBudgetPerson(com.kingdee.eas.basedata.person.PersonInfo item)
    {
        put("budgetPerson", item);
    }
    /**
     * Object: 变更指令单 's 合同 property 
     */
    public com.kingdee.eas.fdc.contract.ContractBillInfo getContractBill()
    {
        return (com.kingdee.eas.fdc.contract.ContractBillInfo)get("contractBill");
    }
    public void setContractBill(com.kingdee.eas.fdc.contract.ContractBillInfo item)
    {
        put("contractBill", item);
    }
    /**
     * Object:变更指令单's 合同编码property 
     */
    public String getContractBillNumber()
    {
        return getString("contractBillNumber");
    }
    public void setContractBillNumber(String item)
    {
        setString("contractBillNumber", item);
    }
    /**
     * Object: 变更指令单 's 变更审批单 property 
     */
    public com.kingdee.eas.fdc.contract.ChangeAuditBillInfo getChangeAudit()
    {
        return (com.kingdee.eas.fdc.contract.ChangeAuditBillInfo)get("changeAudit");
    }
    public void setChangeAudit(com.kingdee.eas.fdc.contract.ChangeAuditBillInfo item)
    {
        put("changeAudit", item);
    }
    /**
     * Object:变更指令单's 变更主题property 
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
     * Object:变更指令单's 紧急程度property 
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
     * Object: 变更指令单 's 工程项目 property 
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
     * Object: 变更指令单 's 承包类型 property 
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
     * Object: 变更指令单 's 专业类型 property 
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
     * Object:变更指令单's 结算方式property 
     */
    public String getBalanceType()
    {
        return getString("balanceType");
    }
    public void setBalanceType(String item)
    {
        setString("balanceType", item);
    }
    /**
     * Object:变更指令单's 是否责任扣款单位property 
     */
    public boolean isIsDeduct()
    {
        return getBoolean("isDeduct");
    }
    public void setIsDeduct(boolean item)
    {
        setBoolean("isDeduct", item);
    }
    /**
     * Object:变更指令单's 扣款金额property 
     */
    public java.math.BigDecimal getDeductAmount()
    {
        return getBigDecimal("deductAmount");
    }
    public void setDeductAmount(java.math.BigDecimal item)
    {
        setBigDecimal("deductAmount", item);
    }
    /**
     * Object:变更指令单's 结算金额property 
     */
    public java.math.BigDecimal getBalanceAmount()
    {
        return getBigDecimal("balanceAmount");
    }
    public void setBalanceAmount(java.math.BigDecimal item)
    {
        setBigDecimal("balanceAmount", item);
    }
    /**
     * Object: 变更指令单 's 主送单位 property 
     */
    public com.kingdee.eas.basedata.master.cssp.SupplierInfo getMainSupp()
    {
        return (com.kingdee.eas.basedata.master.cssp.SupplierInfo)get("mainSupp");
    }
    public void setMainSupp(com.kingdee.eas.basedata.master.cssp.SupplierInfo item)
    {
        put("mainSupp", item);
    }
    /**
     * Object:变更指令单's 扣款原因property 
     */
    public String getDeductReason()
    {
        return getString("deductReason");
    }
    public void setDeductReason(String item)
    {
        setString("deductReason", item);
    }
    /**
     * Object:变更指令单's 附图情况property 
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
     * Object: 变更指令单 's 分录 property 
     */
    public com.kingdee.eas.fdc.contract.ContractChangeEntryCollection getEntrys()
    {
        return (com.kingdee.eas.fdc.contract.ContractChangeEntryCollection)get("entrys");
    }
    /**
     * Object:变更指令单's 执行情况property 
     */
    public int getImplement()
    {
        return getInt("implement");
    }
    public void setImplement(int item)
    {
        setInt("implement", item);
    }
    /**
     * Object:变更指令单's 本次执行说明property 
     */
    public String getDisThisTime()
    {
        return getString("disThisTime");
    }
    public void setDisThisTime(String item)
    {
        setString("disThisTime", item);
    }
    /**
     * Object:变更指令单's 未执行说明property 
     */
    public String getImpleCondition()
    {
        return getString("impleCondition");
    }
    public void setImpleCondition(String item)
    {
        setString("impleCondition", item);
    }
    /**
     * Object:变更指令单's 工程项目property 
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
     * Object:变更指令单's 变更类型property 
     */
    public String getChangeTypeName()
    {
        return getString("changeTypeName");
    }
    public void setChangeTypeName(String item)
    {
        setString("changeTypeName", item);
    }
    /**
     * Object:变更指令单's 承包类型property 
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
     * Object:变更指令单's 专业类型property 
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
     * Object:变更指令单's 审批单编号property 
     */
    public String getChangeAuditNumber()
    {
        return getString("changeAuditNumber");
    }
    public void setChangeAuditNumber(String item)
    {
        setString("changeAuditNumber", item);
    }
    /**
     * Object:变更指令单's 是否结算property 
     */
    public boolean isHasSettled()
    {
        return getBoolean("hasSettled");
    }
    public void setHasSettled(boolean item)
    {
        setBoolean("hasSettled", item);
    }
    /**
     * Object: 变更指令单 's 无效成本原因 property 
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
     * Object:变更指令单's 无效成本金额property 
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
     * Object:变更指令单's 单据来源方式property 
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
     * Object:变更指令单's 合同是否结算property 
     */
    public boolean isIsConSetted()
    {
        return getBoolean("isConSetted");
    }
    public void setIsConSetted(boolean item)
    {
        setBoolean("isConSetted", item);
    }
    /**
     * Object:变更指令单's 拆分状态property 
     */
    public com.kingdee.eas.fdc.basedata.CostSplitStateEnum getSplitState()
    {
        return com.kingdee.eas.fdc.basedata.CostSplitStateEnum.getEnum(getString("splitState"));
    }
    public void setSplitState(com.kingdee.eas.fdc.basedata.CostSplitStateEnum item)
    {
		if (item != null) {
        setString("splitState", item.getValue());
		}
    }
    /**
     * Object:变更指令单's 是否进入动态成本property 
     */
    public boolean isIsCostSplit()
    {
        return getBoolean("isCostSplit");
    }
    public void setIsCostSplit(boolean item)
    {
        setBoolean("isCostSplit", item);
    }
    /**
     * Object: 变更指令单 's  property 
     */
    public com.kingdee.eas.basedata.assistant.CurrencyInfo getCurrency()
    {
        return (com.kingdee.eas.basedata.assistant.CurrencyInfo)get("currency");
    }
    public void setCurrency(com.kingdee.eas.basedata.assistant.CurrencyInfo item)
    {
        put("currency", item);
    }
    /**
     * Object:变更指令单's 汇率property 
     */
    public java.math.BigDecimal getExRate()
    {
        return getBigDecimal("exRate");
    }
    public void setExRate(java.math.BigDecimal item)
    {
        setBigDecimal("exRate", item);
    }
    /**
     * Object:变更指令单's 结算金额原币property 
     */
    public java.math.BigDecimal getOriBalanceAmount()
    {
        return getBigDecimal("oriBalanceAmount");
    }
    public void setOriBalanceAmount(java.math.BigDecimal item)
    {
        setBigDecimal("oriBalanceAmount", item);
    }
    /**
     * Object:变更指令单's 签证后单据的结算审批状态property 
     */
    public com.kingdee.eas.fdc.contract.ConChangeBillSettAfterSignEnum getForSettAfterSign()
    {
        return com.kingdee.eas.fdc.contract.ConChangeBillSettAfterSignEnum.getEnum(getString("forSettAfterSign"));
    }
    public void setForSettAfterSign(com.kingdee.eas.fdc.contract.ConChangeBillSettAfterSignEnum item)
    {
		if (item != null) {
        setString("forSettAfterSign", item.getValue());
		}
    }
    /**
     * Object:变更指令单's 结算时在工作流中的原币金额property 
     */
    public java.math.BigDecimal getSettAuditAmt()
    {
        return getBigDecimal("settAuditAmt");
    }
    public void setSettAuditAmt(java.math.BigDecimal item)
    {
        setBigDecimal("settAuditAmt", item);
    }
    /**
     * Object:变更指令单's 结算时在工作流中的汇率property 
     */
    public java.math.BigDecimal getSettAuditExRate()
    {
        return getBigDecimal("settAuditExRate");
    }
    public void setSettAuditExRate(java.math.BigDecimal item)
    {
        setBigDecimal("settAuditExRate", item);
    }
    /**
     * Object:变更指令单's 提出方property 
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
     * Object: 变更指令单 's 施工单位 property 
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
     * Object:变更指令单's 施工部位property 
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
     * Object: 变更指令单 's 签证类型 property 
     */
    public com.kingdee.eas.fdc.basedata.VisaTypeInfo getVisaType()
    {
        return (com.kingdee.eas.fdc.basedata.VisaTypeInfo)get("visaType");
    }
    public void setVisaType(com.kingdee.eas.fdc.basedata.VisaTypeInfo item)
    {
        put("visaType", item);
    }
    /**
     * Object: 变更指令单 's 提出单位 property 
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
     * Object:变更指令单's 是否在暂估价合同变更序时簿上新增单据property 
     */
    public boolean isIsFromTempEvalChange()
    {
        return getBoolean("isFromTempEvalChange");
    }
    public void setIsFromTempEvalChange(boolean item)
    {
        setBoolean("isFromTempEvalChange", item);
    }
    /**
     * Object:变更指令单's 原始联系单号property 
     */
    public String getOriginalContactNum()
    {
        return getString("originalContactNum");
    }
    public void setOriginalContactNum(String item)
    {
        setString("originalContactNum", item);
    }
    /**
     * Object:变更指令单's 是否确定变更金额property 
     */
    public boolean isIsSureChangeAmt()
    {
        return getBoolean("isSureChangeAmt");
    }
    public void setIsSureChangeAmt(boolean item)
    {
        setBoolean("isSureChangeAmt", item);
    }
    /**
     * Object:变更指令单's 是否重大变更property 
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
     * Object:变更指令单's 施工方报审金额property 
     */
    public java.math.BigDecimal getConstructPrice()
    {
        return getBigDecimal("constructPrice");
    }
    public void setConstructPrice(java.math.BigDecimal item)
    {
        setBigDecimal("constructPrice", item);
    }
    /**
     * Object: 变更指令单 's 专业类型（多选） 不在使用标准产品里的专业类型 property 
     */
    public com.kingdee.eas.fdc.contract.SpecialtyTypeEntryCollection getMutiSpecialtyType()
    {
        return (com.kingdee.eas.fdc.contract.SpecialtyTypeEntryCollection)get("mutiSpecialtyType");
    }
    /**
     * Object: 变更指令单 's 总承包商 property 
     */
    public com.kingdee.eas.fdc.contract.NewMainSupplierEntryCollection getNewMainSupp()
    {
        return (com.kingdee.eas.fdc.contract.NewMainSupplierEntryCollection)get("newMainSupp");
    }
    /**
     * Object:变更指令单's 是否增减合同property 
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
     * Object:变更指令单's 是否回执单property 
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
     * Object:变更指令单's 费用承担单位property 
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
     * Object:变更指令单's 变更事项说明property 
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
     * Object:变更指令单's 是否费用审定property 
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
     * Object: 变更指令单 's 流程类型 property 
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
        return new BOSObjectType("F2141C04");
    }
}