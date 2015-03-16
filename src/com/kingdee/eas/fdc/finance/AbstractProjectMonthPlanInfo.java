package com.kingdee.eas.fdc.finance;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractProjectMonthPlanInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractProjectMonthPlanInfo()
    {
        this("id");
    }
    protected AbstractProjectMonthPlanInfo(String pkField)
    {
        super(pkField);
        put("entry", new com.kingdee.eas.fdc.finance.ProjectMonthPlanEntryCollection());
    }
    /**
     * Object: 项目月度付款计划 's 工程项目 property 
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
     * Object:项目月度付款计划's 版本号property 
     */
    public int getVersion()
    {
        return getInt("version");
    }
    public void setVersion(int item)
    {
        setInt("version", item);
    }
    /**
     * Object: 项目月度付款计划 's 分录 property 
     */
    public com.kingdee.eas.fdc.finance.ProjectMonthPlanEntryCollection getEntry()
    {
        return (com.kingdee.eas.fdc.finance.ProjectMonthPlanEntryCollection)get("entry");
    }
    /**
     * Object:项目月度付款计划's 是否最新property 
     */
    public boolean isIsLatest()
    {
        return getBoolean("isLatest");
    }
    public void setIsLatest(boolean item)
    {
        setBoolean("isLatest", item);
    }
    /**
     * Object: 项目月度付款计划 's 计划周期 property 
     */
    public com.kingdee.eas.fdc.basedata.PayPlanCycleInfo getCycle()
    {
        return (com.kingdee.eas.fdc.basedata.PayPlanCycleInfo)get("cycle");
    }
    public void setCycle(com.kingdee.eas.fdc.basedata.PayPlanCycleInfo item)
    {
        put("cycle", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("23EAA9BF");
    }
}