package com.kingdee.eas.fdc.finance;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractProjectInvestPlanInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractProjectInvestPlanInfo()
    {
        this("id");
    }
    protected AbstractProjectInvestPlanInfo(String pkField)
    {
        super(pkField);
        put("entry", new com.kingdee.eas.fdc.finance.ProjectInvestPlanEntryCollection());
    }
    /**
     * Object:项目全生命周期资金投入计划's 计划起始时间property 
     */
    public java.util.Date getStartDate()
    {
        return getDate("startDate");
    }
    public void setStartDate(java.util.Date item)
    {
        setDate("startDate", item);
    }
    /**
     * Object:项目全生命周期资金投入计划's 计划截止时间property 
     */
    public java.util.Date getEndDate()
    {
        return getDate("endDate");
    }
    public void setEndDate(java.util.Date item)
    {
        setDate("endDate", item);
    }
    /**
     * Object: 项目全生命周期资金投入计划 's 分录 property 
     */
    public com.kingdee.eas.fdc.finance.ProjectInvestPlanEntryCollection getEntry()
    {
        return (com.kingdee.eas.fdc.finance.ProjectInvestPlanEntryCollection)get("entry");
    }
    /**
     * Object: 项目全生命周期资金投入计划 's 工程项目 property 
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
     * Object:项目全生命周期资金投入计划's 版本号property 
     */
    public java.math.BigDecimal getVersionNumber()
    {
        return getBigDecimal("versionNumber");
    }
    public void setVersionNumber(java.math.BigDecimal item)
    {
        setBigDecimal("versionNumber", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("2E405388");
    }
}