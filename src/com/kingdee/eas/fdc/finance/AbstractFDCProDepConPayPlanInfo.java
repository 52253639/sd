package com.kingdee.eas.fdc.finance;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractFDCProDepConPayPlanInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractFDCProDepConPayPlanInfo()
    {
        this("id");
    }
    protected AbstractFDCProDepConPayPlanInfo(String pkField)
    {
        super(pkField);
        put("noContract", new com.kingdee.eas.fdc.finance.FDCProDepConPayPlanNoContractCollection());
        put("unsettledCon", new com.kingdee.eas.fdc.finance.FDCProDepConPayPlanUnsettledCollection());
        put("hasContract", new com.kingdee.eas.fdc.finance.FDCProDepConPayPlanContractCollection());
    }
    /**
     * Object: 项目月度资金滚动需求计划 's 工程项目 property 
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
     * Object: 项目月度资金滚动需求计划 's 付款计划周期 property 
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
     * Object: 项目月度资金滚动需求计划 's 部门 property 
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
     * Object:项目月度资金滚动需求计划's 年份property 
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
     * Object:项目月度资金滚动需求计划's 月份property 
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
     * Object:项目月度资金滚动需求计划's 编制月份property 
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
     * Object:项目月度资金滚动需求计划's 版本property 
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
     * Object: 项目月度资金滚动需求计划 's 已签定合同分录 property 
     */
    public com.kingdee.eas.fdc.finance.FDCProDepConPayPlanContractCollection getHasContract()
    {
        return (com.kingdee.eas.fdc.finance.FDCProDepConPayPlanContractCollection)get("hasContract");
    }
    /**
     * Object: 项目月度资金滚动需求计划 's 待签订合同分录 property 
     */
    public com.kingdee.eas.fdc.finance.FDCProDepConPayPlanUnsettledCollection getUnsettledCon()
    {
        return (com.kingdee.eas.fdc.finance.FDCProDepConPayPlanUnsettledCollection)get("unsettledCon");
    }
    /**
     * Object: 项目月度资金滚动需求计划 's 无合同分录 property 
     */
    public com.kingdee.eas.fdc.finance.FDCProDepConPayPlanNoContractCollection getNoContract()
    {
        return (com.kingdee.eas.fdc.finance.FDCProDepConPayPlanNoContractCollection)get("noContract");
    }
    /**
     * Object:项目月度资金滚动需求计划's 是否重新汇总property 
     */
    public boolean isIsReSum()
    {
        return getBoolean("isReSum");
    }
    public void setIsReSum(boolean item)
    {
        setBoolean("isReSum", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("B3BEFFE7");
    }
}