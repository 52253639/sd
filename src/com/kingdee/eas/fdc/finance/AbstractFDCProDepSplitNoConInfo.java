package com.kingdee.eas.fdc.finance;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractFDCProDepSplitNoConInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractFDCProDepSplitNoConInfo()
    {
        this("id");
    }
    protected AbstractFDCProDepSplitNoConInfo(String pkField)
    {
        super(pkField);
        put("details", new com.kingdee.eas.fdc.finance.FDCProDepSplitNoConEntryCollection());
    }
    /**
     * Object: null 's 表头 property 
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
     * Object:null's 付款事项编码property 
     */
    public String getPayMatters()
    {
        return getString("payMatters");
    }
    public void setPayMatters(String item)
    {
        setString("payMatters", item);
    }
    /**
     * Object:null's 付款事项名称property 
     */
    public String getPayMattersName()
    {
        return getString("payMattersName");
    }
    public void setPayMattersName(String item)
    {
        setString("payMattersName", item);
    }
    /**
     * Object: null 's 归属项目 property 
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
     * Object:null's 是否拆分到成本科目property 
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
     * Object: null 's 成本科目 property 
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
     * Object: null 's 会计科目 property 
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
     * Object: null 's null property 
     */
    public com.kingdee.eas.fdc.finance.FDCProDepSplitNoConEntryCollection getDetails()
    {
        return (com.kingdee.eas.fdc.finance.FDCProDepSplitNoConEntryCollection)get("details");
    }
    /**
     * Object:null's 是否拆分行property 
     */
    public boolean isIsSplitRow()
    {
        return getBoolean("isSplitRow");
    }
    public void setIsSplitRow(boolean item)
    {
        setBoolean("isSplitRow", item);
    }
    /**
     * Object: null 's 滚动计划分录 property 
     */
    public com.kingdee.eas.fdc.finance.FDCProDepConPayPlanNoContractInfo getFdcProDepEntry()
    {
        return (com.kingdee.eas.fdc.finance.FDCProDepConPayPlanNoContractInfo)get("fdcProDepEntry");
    }
    public void setFdcProDepEntry(com.kingdee.eas.fdc.finance.FDCProDepConPayPlanNoContractInfo item)
    {
        put("fdcProDepEntry", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("056F90DF");
    }
}