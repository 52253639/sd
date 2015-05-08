package com.kingdee.eas.fdc.contract;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractContractWithoutTextInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractContractWithoutTextInfo()
    {
        this("id");
    }
    protected AbstractContractWithoutTextInfo(String pkField)
    {
        super(pkField);
        put("feeEntry", new com.kingdee.eas.fdc.contract.FeeEntryCollection());
        put("traEntry", new com.kingdee.eas.fdc.contract.TraEntryCollection());
        put("bgEntry", new com.kingdee.eas.fdc.contract.ContractWithoutTextBgEntryCollection());
    }
    /**
     * Object: 无文本合同 's 工程项目 property 
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
     * Object:无文本合同's 实际付款日期property 
     */
    public java.util.Date getSignDate()
    {
        return getDate("signDate");
    }
    public void setSignDate(java.util.Date item)
    {
        setDate("signDate", item);
    }
    /**
     * Object: 无文本合同 's 收款人名称 property 
     */
    public com.kingdee.eas.basedata.master.cssp.SupplierInfo getReceiveUnit()
    {
        return (com.kingdee.eas.basedata.master.cssp.SupplierInfo)get("receiveUnit");
    }
    public void setReceiveUnit(com.kingdee.eas.basedata.master.cssp.SupplierInfo item)
    {
        put("receiveUnit", item);
    }
    /**
     * Object: 无文本合同 's 结算方式 property 
     */
    public com.kingdee.eas.basedata.assistant.SettlementTypeInfo getSettlementType()
    {
        return (com.kingdee.eas.basedata.assistant.SettlementTypeInfo)get("settlementType");
    }
    public void setSettlementType(com.kingdee.eas.basedata.assistant.SettlementTypeInfo item)
    {
        put("settlementType", item);
    }
    /**
     * Object: 无文本合同 's 币别 property 
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
     * Object:无文本合同's 财务结算号property 
     */
    public String getFiSettleNo()
    {
        return getString("fiSettleNo");
    }
    public void setFiSettleNo(String item)
    {
        setString("fiSettleNo", item);
    }
    /**
     * Object:无文本合同's 生成付款计划property 
     */
    public boolean isGenPaymentPlan()
    {
        return getBoolean("genPaymentPlan");
    }
    public void setGenPaymentPlan(boolean item)
    {
        setBoolean("genPaymentPlan", item);
    }
    /**
     * Object:无文本合同's 生成付款申请单property 
     */
    public boolean isGenPaymentReque()
    {
        return getBoolean("genPaymentReque");
    }
    public void setGenPaymentReque(boolean item)
    {
        setBoolean("genPaymentReque", item);
    }
    /**
     * Object:无文本合同's 生成付款单property 
     */
    public boolean isGenPaymentBill()
    {
        return getBoolean("genPaymentBill");
    }
    public void setGenPaymentBill(boolean item)
    {
        setBoolean("genPaymentBill", item);
    }
    /**
     * Object:无文本合同's 开户银行property 
     */
    public String getBank()
    {
        return getString("bank");
    }
    public void setBank(String item)
    {
        setString("bank", item);
    }
    /**
     * Object:无文本合同's 银行账号property 
     */
    public String getBankAcct()
    {
        return getString("bankAcct");
    }
    public void setBankAcct(String item)
    {
        setString("bankAcct", item);
    }
    /**
     * Object:无文本合同's 是否成本拆分property 
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
     * Object:无文本合同's 待处理状态property 
     */
    public com.kingdee.eas.fdc.contract.ConSplitExecStateEnum getConSplitExecState()
    {
        return com.kingdee.eas.fdc.contract.ConSplitExecStateEnum.getEnum(getString("conSplitExecState"));
    }
    public void setConSplitExecState(com.kingdee.eas.fdc.contract.ConSplitExecStateEnum item)
    {
		if (item != null) {
        setString("conSplitExecState", item.getValue());
		}
    }
    /**
     * Object:无文本合同's 是否需要付款property 
     */
    public boolean isIsNeedPaid()
    {
        return getBoolean("isNeedPaid");
    }
    public void setIsNeedPaid(boolean item)
    {
        setBoolean("isNeedPaid", item);
    }
    /**
     * Object:无文本合同's 无需付款原因property 
     */
    public String getNoPaidReason()
    {
        return getString("noPaidReason");
    }
    public void setNoPaidReason(String item)
    {
        setString("noPaidReason", item);
    }
    /**
     * Object:无文本合同's 单据来源方式property 
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
     * Object: 无文本合同 's 贷方科目 property 
     */
    public com.kingdee.eas.basedata.master.account.AccountViewInfo getAccount()
    {
        return (com.kingdee.eas.basedata.master.account.AccountViewInfo)get("account");
    }
    public void setAccount(com.kingdee.eas.basedata.master.account.AccountViewInfo item)
    {
        put("account", item);
    }
    /**
     * Object:无文本合同's 拆分状态property 
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
     * Object: 无文本合同 's 合同类型 property 
     */
    public com.kingdee.eas.fdc.basedata.ContractTypeInfo getContractType()
    {
        return (com.kingdee.eas.fdc.basedata.ContractTypeInfo)get("contractType");
    }
    public void setContractType(com.kingdee.eas.fdc.basedata.ContractTypeInfo item)
    {
        put("contractType", item);
    }
    /**
     * Object: 无文本合同 's 合同费用项目 property 
     */
    public com.kingdee.eas.fdc.basedata.ContractChargeTypeInfo getConChargeType()
    {
        return (com.kingdee.eas.fdc.basedata.ContractChargeTypeInfo)get("conChargeType");
    }
    public void setConChargeType(com.kingdee.eas.fdc.basedata.ContractChargeTypeInfo item)
    {
        put("conChargeType", item);
    }
    /**
     * Object: 无文本合同 's 合同基础信息 property 
     */
    public com.kingdee.eas.fdc.contract.ContractBaseDataInfo getContractBaseData()
    {
        return (com.kingdee.eas.fdc.contract.ContractBaseDataInfo)get("contractBaseData");
    }
    public void setContractBaseData(com.kingdee.eas.fdc.contract.ContractBaseDataInfo item)
    {
        put("contractBaseData", item);
    }
    /**
     * Object:无文本合同's 发票号property 
     */
    public String getInvoiceNumber()
    {
        return getString("invoiceNumber");
    }
    public void setInvoiceNumber(String item)
    {
        setString("invoiceNumber", item);
    }
    /**
     * Object:无文本合同's 发票金额property 
     */
    public java.math.BigDecimal getInvoiceAmt()
    {
        return getBigDecimal("invoiceAmt");
    }
    public void setInvoiceAmt(java.math.BigDecimal item)
    {
        setBigDecimal("invoiceAmt", item);
    }
    /**
     * Object:无文本合同's 累计发票金额property 
     */
    public double getAllInvoiceAmt()
    {
        return getDouble("allInvoiceAmt");
    }
    public void setAllInvoiceAmt(double item)
    {
        setDouble("allInvoiceAmt", item);
    }
    /**
     * Object:无文本合同's 发票日期property 
     */
    public java.util.Date getInvoiceDate()
    {
        return getDate("invoiceDate");
    }
    public void setInvoiceDate(java.util.Date item)
    {
        setDate("invoiceDate", item);
    }
    /**
     * Object: 无文本合同 's 用款部门 property 
     */
    public com.kingdee.eas.basedata.org.AdminOrgUnitInfo getUseDepartment()
    {
        return (com.kingdee.eas.basedata.org.AdminOrgUnitInfo)get("useDepartment");
    }
    public void setUseDepartment(com.kingdee.eas.basedata.org.AdminOrgUnitInfo item)
    {
        put("useDepartment", item);
    }
    /**
     * Object: 无文本合同 's 框架合约 property 
     */
    public com.kingdee.eas.fdc.contract.programming.ProgrammingContractInfo getProgrammingContract()
    {
        return (com.kingdee.eas.fdc.contract.programming.ProgrammingContractInfo)get("programmingContract");
    }
    public void setProgrammingContract(com.kingdee.eas.fdc.contract.programming.ProgrammingContractInfo item)
    {
        put("programmingContract", item);
    }
    /**
     * Object: 无文本合同 's 合同月度滚动计划无合同分录 property 
     */
    public com.kingdee.eas.fdc.finance.FDCDepConPayPlanNoContractInfo getFdcDepConPlan()
    {
        return (com.kingdee.eas.fdc.finance.FDCDepConPayPlanNoContractInfo)get("fdcDepConPlan");
    }
    public void setFdcDepConPlan(com.kingdee.eas.fdc.finance.FDCDepConPayPlanNoContractInfo item)
    {
        put("fdcDepConPlan", item);
    }
    /**
     * Object:无文本合同's 是否预算控制property 
     */
    public boolean isIsBgControl()
    {
        return getBoolean("isBgControl");
    }
    public void setIsBgControl(boolean item)
    {
        setBoolean("isBgControl", item);
    }
    /**
     * Object: 无文本合同 's 申请人 property 
     */
    public com.kingdee.eas.basedata.person.PersonInfo getApplier()
    {
        return (com.kingdee.eas.basedata.person.PersonInfo)get("applier");
    }
    public void setApplier(com.kingdee.eas.basedata.person.PersonInfo item)
    {
        put("applier", item);
    }
    /**
     * Object: 无文本合同 's 申请部门 property 
     */
    public com.kingdee.eas.basedata.org.AdminOrgUnitInfo getApplierOrgUnit()
    {
        return (com.kingdee.eas.basedata.org.AdminOrgUnitInfo)get("applierOrgUnit");
    }
    public void setApplierOrgUnit(com.kingdee.eas.basedata.org.AdminOrgUnitInfo item)
    {
        put("applierOrgUnit", item);
    }
    /**
     * Object: 无文本合同 's 申请人公司 property 
     */
    public com.kingdee.eas.basedata.org.CompanyOrgUnitInfo getApplierCompany()
    {
        return (com.kingdee.eas.basedata.org.CompanyOrgUnitInfo)get("applierCompany");
    }
    public void setApplierCompany(com.kingdee.eas.basedata.org.CompanyOrgUnitInfo item)
    {
        put("applierCompany", item);
    }
    /**
     * Object: 无文本合同 's 预算承担部门 property 
     */
    public com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo getCostedDept()
    {
        return (com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo)get("costedDept");
    }
    public void setCostedDept(com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo item)
    {
        put("costedDept", item);
    }
    /**
     * Object: 无文本合同 's 预算承担公司 property 
     */
    public com.kingdee.eas.basedata.org.CompanyOrgUnitInfo getCostedCompany()
    {
        return (com.kingdee.eas.basedata.org.CompanyOrgUnitInfo)get("costedCompany");
    }
    public void setCostedCompany(com.kingdee.eas.basedata.org.CompanyOrgUnitInfo item)
    {
        put("costedCompany", item);
    }
    /**
     * Object: 无文本合同 's 组织 property 
     */
    public com.kingdee.eas.basedata.org.FullOrgUnitInfo getCompany()
    {
        return (com.kingdee.eas.basedata.org.FullOrgUnitInfo)get("company");
    }
    public void setCompany(com.kingdee.eas.basedata.org.FullOrgUnitInfo item)
    {
        put("company", item);
    }
    /**
     * Object: 无文本合同 's 费用清单 property 
     */
    public com.kingdee.eas.fdc.contract.ContractWithoutTextBgEntryCollection getBgEntry()
    {
        return (com.kingdee.eas.fdc.contract.ContractWithoutTextBgEntryCollection)get("bgEntry");
    }
    /**
     * Object:无文本合同's 有无发票property 
     */
    public boolean isIsInvoice()
    {
        return getBoolean("isInvoice");
    }
    public void setIsInvoice(boolean item)
    {
        setBoolean("isInvoice", item);
    }
    /**
     * Object: 无文本合同 's 付款类型 property 
     */
    public com.kingdee.eas.fi.cas.PaymentBillTypeInfo getPayBillType()
    {
        return (com.kingdee.eas.fi.cas.PaymentBillTypeInfo)get("payBillType");
    }
    public void setPayBillType(com.kingdee.eas.fi.cas.PaymentBillTypeInfo item)
    {
        put("payBillType", item);
    }
    /**
     * Object: 无文本合同 's 付款事项 property 
     */
    public com.kingdee.eas.fdc.contract.PayContentTypeInfo getPayContentType()
    {
        return (com.kingdee.eas.fdc.contract.PayContentTypeInfo)get("payContentType");
    }
    public void setPayContentType(com.kingdee.eas.fdc.contract.PayContentTypeInfo item)
    {
        put("payContentType", item);
    }
    /**
     * Object: 无文本合同 's 职员 property 
     */
    public com.kingdee.eas.basedata.person.PersonInfo getPerson()
    {
        return (com.kingdee.eas.basedata.person.PersonInfo)get("person");
    }
    public void setPerson(com.kingdee.eas.basedata.person.PersonInfo item)
    {
        put("person", item);
    }
    /**
     * Object:无文本合同's 费用明细类型property 
     */
    public com.kingdee.eas.fdc.contract.FeeTypeEnum getFeeType()
    {
        return com.kingdee.eas.fdc.contract.FeeTypeEnum.getEnum(getString("feeType"));
    }
    public void setFeeType(com.kingdee.eas.fdc.contract.FeeTypeEnum item)
    {
		if (item != null) {
        setString("feeType", item.getValue());
		}
    }
    /**
     * Object: 无文本合同 's 费用报销类分录 property 
     */
    public com.kingdee.eas.fdc.contract.FeeEntryCollection getFeeEntry()
    {
        return (com.kingdee.eas.fdc.contract.FeeEntryCollection)get("feeEntry");
    }
    /**
     * Object: 无文本合同 's 差旅费报销类分录 property 
     */
    public com.kingdee.eas.fdc.contract.TraEntryCollection getTraEntry()
    {
        return (com.kingdee.eas.fdc.contract.TraEntryCollection)get("traEntry");
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("3D9A5388");
    }
}