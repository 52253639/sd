package com.kingdee.eas.fdc.finance;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractFDCDepConPayPlanBillInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractFDCDepConPayPlanBillInfo()
    {
        this("id");
    }
    protected AbstractFDCDepConPayPlanBillInfo(String pkField)
    {
        super(pkField);
        put("noContract", new com.kingdee.eas.fdc.finance.FDCDepConPayPlanNoContractCollection());
        put("unsettledCon", new com.kingdee.eas.fdc.finance.FDCDepConPayPlanUnsettledConCollection());
        put("hasContract", new com.kingdee.eas.fdc.finance.FDCDepConPayPlanContractCollection());
    }
    /**
     * Object: 合同月度滚动付款计划 's 工程项目 property 
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
     * Object: 合同月度滚动付款计划 's 房地产预算期间 property 
     */
    public com.kingdee.eas.fdc.finance.FDCBudgetPeriodInfo getFdcPeriod()
    {
        return (com.kingdee.eas.fdc.finance.FDCBudgetPeriodInfo)get("fdcPeriod");
    }
    public void setFdcPeriod(com.kingdee.eas.fdc.finance.FDCBudgetPeriodInfo item)
    {
        put("fdcPeriod", item);
    }
    /**
     * Object: 合同月度滚动付款计划 's 付款计划周期 property 
     */
    public com.kingdee.eas.fdc.basedata.PayPlanCycleInfo getPayPlanCycle()
    {
        return (com.kingdee.eas.fdc.basedata.PayPlanCycleInfo)get("payPlanCycle");
    }
    public void setPayPlanCycle(com.kingdee.eas.fdc.basedata.PayPlanCycleInfo item)
    {
        put("payPlanCycle", item);
    }
    /**
     * Object: 合同月度滚动付款计划 's 部门 property 
     */
    public com.kingdee.eas.basedata.org.AdminOrgUnitInfo getDeptment()
    {
        return (com.kingdee.eas.basedata.org.AdminOrgUnitInfo)get("deptment");
    }
    public void setDeptment(com.kingdee.eas.basedata.org.AdminOrgUnitInfo item)
    {
        put("deptment", item);
    }
    /**
     * Object:合同月度滚动付款计划's 年份property 
     */
    public int getYear()
    {
        return getInt("year");
    }
    public void setYear(int item)
    {
        setInt("year", item);
    }
    /**
     * Object:合同月度滚动付款计划's 月份property 
     */
    public int getMonth()
    {
        return getInt("month");
    }
    public void setMonth(int item)
    {
        setInt("month", item);
    }
    /**
     * Object:合同月度滚动付款计划's 编制月份property 
     */
    public int getEditMonth()
    {
        return getInt("editMonth");
    }
    public void setEditMonth(int item)
    {
        setInt("editMonth", item);
    }
    /**
     * Object:合同月度滚动付款计划's 版本property 
     */
    public String getVersion()
    {
        return getString("version");
    }
    public void setVersion(String item)
    {
        setString("version", item);
    }
    /**
     * Object: 合同月度滚动付款计划 's 已签定合同付款计划 property 
     */
    public com.kingdee.eas.fdc.finance.FDCDepConPayPlanContractCollection getHasContract()
    {
        return (com.kingdee.eas.fdc.finance.FDCDepConPayPlanContractCollection)get("hasContract");
    }
    /**
     * Object: 合同月度滚动付款计划 's 待签定合同付款计划 property 
     */
    public com.kingdee.eas.fdc.finance.FDCDepConPayPlanUnsettledConCollection getUnsettledCon()
    {
        return (com.kingdee.eas.fdc.finance.FDCDepConPayPlanUnsettledConCollection)get("unsettledCon");
    }
    /**
     * Object: 合同月度滚动付款计划 's 无合同付款计划 property 
     */
    public com.kingdee.eas.fdc.finance.FDCDepConPayPlanNoContractCollection getNoContract()
    {
        return (com.kingdee.eas.fdc.finance.FDCDepConPayPlanNoContractCollection)get("noContract");
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("F288954F");
    }
}