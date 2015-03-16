package com.kingdee.eas.fdc.aimcost;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractMeasureIncomeInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractMeasureIncomeInfo()
    {
        this("id");
    }
    protected AbstractMeasureIncomeInfo(String pkField)
    {
        super(pkField);
        put("incomeEntry", new com.kingdee.eas.fdc.aimcost.MeasureIncomeEntryCollection());
    }
    /**
     * Object: 收入测算 's 工程项目 property 
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
     * Object: 收入测算 's 分录 property 
     */
    public com.kingdee.eas.fdc.aimcost.MeasureIncomeEntryCollection getIncomeEntry()
    {
        return (com.kingdee.eas.fdc.aimcost.MeasureIncomeEntryCollection)get("incomeEntry");
    }
    /**
     * Object:收入测算's 版本号property 
     */
    public String getVersionNumber()
    {
        return getString("versionNumber");
    }
    public void setVersionNumber(String item)
    {
        setString("versionNumber", item);
    }
    /**
     * Object:收入测算's 版本名称property 
     */
    public String getVersionName()
    {
        return getString("versionName");
    }
    public void setVersionName(String item)
    {
        setString("versionName", item);
    }
    /**
     * Object:收入测算's 修订日期property 
     */
    public java.util.Date getRecenseDate()
    {
        return getDate("recenseDate");
    }
    public void setRecenseDate(java.util.Date item)
    {
        setDate("recenseDate", item);
    }
    /**
     * Object:收入测算's 是否最终版本property 
     */
    public boolean isIsLastVersion()
    {
        return getBoolean("isLastVersion");
    }
    public void setIsLastVersion(boolean item)
    {
        setBoolean("isLastVersion", item);
    }
    /**
     * Object: 收入测算 's 项目系列 property 
     */
    public com.kingdee.eas.fdc.basedata.ProjectTypeInfo getProjectType()
    {
        return (com.kingdee.eas.fdc.basedata.ProjectTypeInfo)get("projectType");
    }
    public void setProjectType(com.kingdee.eas.fdc.basedata.ProjectTypeInfo item)
    {
        put("projectType", item);
    }
    /**
     * Object:收入测算's 是否目标测算property 
     */
    public boolean isIsAimMeasure()
    {
        return getBoolean("isAimMeasure");
    }
    public void setIsAimMeasure(boolean item)
    {
        setBoolean("isAimMeasure", item);
    }
    /**
     * Object:收入测算's 主版本号property 
     */
    public int getMainVerNo()
    {
        return getInt("mainVerNo");
    }
    public void setMainVerNo(int item)
    {
        setInt("mainVerNo", item);
    }
    /**
     * Object:收入测算's 子版本号property 
     */
    public int getSubVerNo()
    {
        return getInt("subVerNo");
    }
    public void setSubVerNo(int item)
    {
        setInt("subVerNo", item);
    }
    /**
     * Object: 收入测算 's 测算阶段 property 
     */
    public com.kingdee.eas.fdc.basedata.MeasureStageInfo getMeasureStage()
    {
        return (com.kingdee.eas.fdc.basedata.MeasureStageInfo)get("measureStage");
    }
    public void setMeasureStage(com.kingdee.eas.fdc.basedata.MeasureStageInfo item)
    {
        put("measureStage", item);
    }
    /**
     * Object:收入测算's 成本测算idproperty 
     */
    public String getSrcMeasureCostId()
    {
        return getString("srcMeasureCostId");
    }
    public void setSrcMeasureCostId(String item)
    {
        setString("srcMeasureCostId", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("C1C610B0");
    }
}