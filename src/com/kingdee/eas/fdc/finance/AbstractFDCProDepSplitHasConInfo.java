package com.kingdee.eas.fdc.finance;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractFDCProDepSplitHasConInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractFDCProDepSplitHasConInfo()
    {
        this("id");
    }
    protected AbstractFDCProDepSplitHasConInfo(String pkField)
    {
        super(pkField);
        put("details", new com.kingdee.eas.fdc.finance.FDCProDepSplitHasConEntryCollection());
    }
    /**
     * Object: 滚动计划付款已签订合同拆分 's 拆分单据 property 
     */
    public com.kingdee.eas.fdc.finance.FDCProDepSplitInfo getHead()
    {
        return (com.kingdee.eas.fdc.finance.FDCProDepSplitInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.finance.FDCProDepSplitInfo item)
    {
        put("head", item);
    }
    /**
     * Object: 滚动计划付款已签订合同拆分 's 滚动计划分录 property 
     */
    public com.kingdee.eas.fdc.finance.FDCProDepConPayPlanContractInfo getFdcProDepEntry()
    {
        return (com.kingdee.eas.fdc.finance.FDCProDepConPayPlanContractInfo)get("fdcProDepEntry");
    }
    public void setFdcProDepEntry(com.kingdee.eas.fdc.finance.FDCProDepConPayPlanContractInfo item)
    {
        put("fdcProDepEntry", item);
    }
    /**
     * Object: 滚动计划付款已签订合同拆分 's 合同 property 
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
     * Object:滚动计划付款已签订合同拆分's 合同名称property 
     */
    public String getContractName()
    {
        return getString("contractName");
    }
    public void setContractName(String item)
    {
        setString("contractName", item);
    }
    /**
     * Object:滚动计划付款已签订合同拆分's 合同最新造价property 
     */
    public java.math.BigDecimal getContractPrice()
    {
        return getBigDecimal("contractPrice");
    }
    public void setContractPrice(java.math.BigDecimal item)
    {
        setBigDecimal("contractPrice", item);
    }
    /**
     * Object: 滚动计划付款已签订合同拆分 's 归属项目 property 
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
     * Object:滚动计划付款已签订合同拆分's 是否拆分成成本科目property 
     */
    public boolean isIsSplitCost()
    {
        return getBoolean("isSplitCost");
    }
    public void setIsSplitCost(boolean item)
    {
        setBoolean("isSplitCost", item);
    }
    /**
     * Object: 滚动计划付款已签订合同拆分 's 成本科目 property 
     */
    public com.kingdee.eas.fdc.basedata.CostAccountInfo getCostAccount()
    {
        return (com.kingdee.eas.fdc.basedata.CostAccountInfo)get("costAccount");
    }
    public void setCostAccount(com.kingdee.eas.fdc.basedata.CostAccountInfo item)
    {
        put("costAccount", item);
    }
    /**
     * Object: 滚动计划付款已签订合同拆分 's 会计科目 property 
     */
    public com.kingdee.eas.basedata.master.account.AccountViewInfo getAccountView()
    {
        return (com.kingdee.eas.basedata.master.account.AccountViewInfo)get("accountView");
    }
    public void setAccountView(com.kingdee.eas.basedata.master.account.AccountViewInfo item)
    {
        put("accountView", item);
    }
    /**
     * Object: 滚动计划付款已签订合同拆分 's 拆分明细 property 
     */
    public com.kingdee.eas.fdc.finance.FDCProDepSplitHasConEntryCollection getDetails()
    {
        return (com.kingdee.eas.fdc.finance.FDCProDepSplitHasConEntryCollection)get("details");
    }
    /**
     * Object:滚动计划付款已签订合同拆分's 是否拆分行property 
     */
    public boolean isIsSplitRow()
    {
        return getBoolean("isSplitRow");
    }
    public void setIsSplitRow(boolean item)
    {
        setBoolean("isSplitRow", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("9D955B6A");
    }
}