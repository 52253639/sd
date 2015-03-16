package com.kingdee.eas.fdc.finance;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractFDCProDepSplitUnConInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractFDCProDepSplitUnConInfo()
    {
        this("id");
    }
    protected AbstractFDCProDepSplitUnConInfo(String pkField)
    {
        super(pkField);
        put("details", new com.kingdee.eas.fdc.finance.FDCProDepSplitUnConEntryCollection());
    }
    /**
     * Object: 计划拆分-待签订合同分录 's 表头 property 
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
     * Object: 计划拆分-待签订合同分录 's 滚动计划分录 property 
     */
    public com.kingdee.eas.fdc.finance.FDCProDepConPayPlanUnsettledInfo getFdcProDepEntry()
    {
        return (com.kingdee.eas.fdc.finance.FDCProDepConPayPlanUnsettledInfo)get("fdcProDepEntry");
    }
    public void setFdcProDepEntry(com.kingdee.eas.fdc.finance.FDCProDepConPayPlanUnsettledInfo item)
    {
        put("fdcProDepEntry", item);
    }
    /**
     * Object:计划拆分-待签订合同分录's 待签订合同编码property 
     */
    public String getUnConNumber()
    {
        return getString("unConNumber");
    }
    public void setUnConNumber(String item)
    {
        setString("unConNumber", item);
    }
    /**
     * Object:计划拆分-待签订合同分录's 待签订合同名称property 
     */
    public String getUnConName()
    {
        return getString("unConName");
    }
    public void setUnConName(String item)
    {
        setString("unConName", item);
    }
    /**
     * Object:计划拆分-待签订合同分录's 预计签约金额property 
     */
    public java.math.BigDecimal getPlanAmount()
    {
        return getBigDecimal("planAmount");
    }
    public void setPlanAmount(java.math.BigDecimal item)
    {
        setBigDecimal("planAmount", item);
    }
    /**
     * Object: 计划拆分-待签订合同分录 's 归属项目 property 
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
     * Object:计划拆分-待签订合同分录's 是否拆分到成本科目property 
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
     * Object: 计划拆分-待签订合同分录 's 成本科目 property 
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
     * Object: 计划拆分-待签订合同分录 's 会计科目 property 
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
     * Object: 计划拆分-待签订合同分录 's null property 
     */
    public com.kingdee.eas.fdc.finance.FDCProDepSplitUnConEntryCollection getDetails()
    {
        return (com.kingdee.eas.fdc.finance.FDCProDepSplitUnConEntryCollection)get("details");
    }
    /**
     * Object:计划拆分-待签订合同分录's 是否拆分行property 
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
        return new BOSObjectType("05D1C107");
    }
}