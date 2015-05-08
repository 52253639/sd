package com.kingdee.eas.fdc.schedule;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractFDCScheduleInfo extends com.kingdee.eas.fdc.schedule.framework.ScheduleBaseInfo implements Serializable 
{
    public AbstractFDCScheduleInfo()
    {
        this("id");
    }
    protected AbstractFDCScheduleInfo(String pkField)
    {
        super(pkField);
        put("dispColumns", new com.kingdee.eas.fdc.schedule.FDCSchTaskDispColumnsCollection());
        put("taskEntrys", new com.kingdee.eas.fdc.schedule.FDCScheduleTaskCollection());
    }
    /**
     * Object: 房地产进度计划 's 工程项目 property 
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
     * Object: 房地产进度计划 's 任务分录 property 
     */
    public com.kingdee.eas.fdc.schedule.FDCScheduleTaskCollection getTaskEntrys()
    {
        return (com.kingdee.eas.fdc.schedule.FDCScheduleTaskCollection)get("taskEntrys");
    }
    /**
     * Object: 房地产进度计划 's 列显示 property 
     */
    public com.kingdee.eas.fdc.schedule.FDCSchTaskDispColumnsCollection getDispColumns()
    {
        return (com.kingdee.eas.fdc.schedule.FDCSchTaskDispColumnsCollection)get("dispColumns");
    }
    /**
     * Object: 房地产进度计划 's 项目日历 property 
     */
    public com.kingdee.eas.fdc.schedule.framework.ScheduleCalendarInfo getCalendar()
    {
        return (com.kingdee.eas.fdc.schedule.framework.ScheduleCalendarInfo)get("calendar");
    }
    public void setCalendar(com.kingdee.eas.fdc.schedule.framework.ScheduleCalendarInfo item)
    {
        put("calendar", item);
    }
    /**
     * Object: 房地产进度计划 's 责任人 property 
     */
    public com.kingdee.eas.basedata.person.PersonInfo getAdminPerson()
    {
        return (com.kingdee.eas.basedata.person.PersonInfo)get("adminPerson");
    }
    public void setAdminPerson(com.kingdee.eas.basedata.person.PersonInfo item)
    {
        put("adminPerson", item);
    }
    /**
     * Object:房地产进度计划's 版本property 
     */
    public float getVersion()
    {
        return getFloat("version");
    }
    public void setVersion(float item)
    {
        setFloat("version", item);
    }
    /**
     * Object:房地产进度计划's 开工日期property 
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
     * Object:房地产进度计划's 总工期property 
     */
    public java.math.BigDecimal getTotalTimes()
    {
        return getBigDecimal("totalTimes");
    }
    public void setTotalTimes(java.math.BigDecimal item)
    {
        setBigDecimal("totalTimes", item);
    }
    /**
     * Object:房地产进度计划's 有效工期property 
     */
    public java.math.BigDecimal getEffectTimes()
    {
        return getBigDecimal("effectTimes");
    }
    public void setEffectTimes(java.math.BigDecimal item)
    {
        setBigDecimal("effectTimes", item);
    }
    /**
     * Object:房地产进度计划's 自然工期property 
     */
    public java.math.BigDecimal getNatureTimes()
    {
        return getBigDecimal("natureTimes");
    }
    public void setNatureTimes(java.math.BigDecimal item)
    {
        setBigDecimal("natureTimes", item);
    }
    /**
     * Object: 房地产进度计划 's 计划类型 property 
     */
    public com.kingdee.eas.fdc.schedule.TaskTypeInfo getScheduleType()
    {
        return (com.kingdee.eas.fdc.schedule.TaskTypeInfo)get("scheduleType");
    }
    public void setScheduleType(com.kingdee.eas.fdc.schedule.TaskTypeInfo item)
    {
        put("scheduleType", item);
    }
    /**
     * Object: 房地产进度计划 's 责任部门 property 
     */
    public com.kingdee.eas.basedata.org.FullOrgUnitInfo getAdminDept()
    {
        return (com.kingdee.eas.basedata.org.FullOrgUnitInfo)get("adminDept");
    }
    public void setAdminDept(com.kingdee.eas.basedata.org.FullOrgUnitInfo item)
    {
        put("adminDept", item);
    }
    /**
     * Object:房地产进度计划's 最新版本property 
     */
    public boolean isIsLatestVer()
    {
        return getBoolean("isLatestVer");
    }
    public void setIsLatestVer(boolean item)
    {
        setBoolean("isLatestVer", item);
    }
    /**
     * Object:房地产进度计划's 版本形成原因property 
     */
    public com.kingdee.eas.fdc.schedule.ScheduleCreateReasonEnum getCreateReason()
    {
        return com.kingdee.eas.fdc.schedule.ScheduleCreateReasonEnum.getEnum(getString("createReason"));
    }
    public void setCreateReason(com.kingdee.eas.fdc.schedule.ScheduleCreateReasonEnum item)
    {
		if (item != null) {
        setString("createReason", item.getValue());
		}
    }
    /**
     * Object:房地产进度计划's 完工时间property 
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
     * Object: 房地产进度计划 's 基准版本 property 
     */
    public com.kingdee.eas.fdc.schedule.ScheduleVerManagerInfo getBaseVer()
    {
        return (com.kingdee.eas.fdc.schedule.ScheduleVerManagerInfo)get("baseVer");
    }
    public void setBaseVer(com.kingdee.eas.fdc.schedule.ScheduleVerManagerInfo item)
    {
        put("baseVer", item);
    }
    /**
     * Object: 房地产进度计划 's 任务属性类型 property 
     */
    public com.kingdee.eas.fdc.schedule.ScheduleCodingTypeInfo getScheduleCodingType()
    {
        return (com.kingdee.eas.fdc.schedule.ScheduleCodingTypeInfo)get("scheduleCodingType");
    }
    public void setScheduleCodingType(com.kingdee.eas.fdc.schedule.ScheduleCodingTypeInfo item)
    {
        put("scheduleCodingType", item);
    }
    /**
     * Object:房地产进度计划's 版本名称property 
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
     * Object:房地产进度计划's 版本调整日期property 
     */
    public java.util.Date getVersionDate()
    {
        return getDate("versionDate");
    }
    public void setVersionDate(java.util.Date item)
    {
        setDate("versionDate", item);
    }
    /**
     * Object: 房地产进度计划 's 调整原因 property 
     */
    public com.kingdee.eas.fdc.schedule.PlanAdjustReasonInfo getAdjustReason()
    {
        return (com.kingdee.eas.fdc.schedule.PlanAdjustReasonInfo)get("adjustReason");
    }
    public void setAdjustReason(com.kingdee.eas.fdc.schedule.PlanAdjustReasonInfo item)
    {
        put("adjustReason", item);
    }
    /**
     * Object: 房地产进度计划 's 项目专项 property 
     */
    public com.kingdee.eas.fdc.schedule.ProjectSpecialInfo getProjectSpecial()
    {
        return (com.kingdee.eas.fdc.schedule.ProjectSpecialInfo)get("projectSpecial");
    }
    public void setProjectSpecial(com.kingdee.eas.fdc.schedule.ProjectSpecialInfo item)
    {
        put("projectSpecial", item);
    }
    /**
     * Object:房地产进度计划's srcIDproperty 
     */
    public String getSrcID()
    {
        return getString("srcID");
    }
    public void setSrcID(String item)
    {
        setString("srcID", item);
    }
    /**
     * Object:房地产进度计划's 是否考核版本property 
     */
    public boolean isIsCheckVersion()
    {
        return getBoolean("isCheckVersion");
    }
    public void setIsCheckVersion(boolean item)
    {
        setBoolean("isCheckVersion", item);
    }
    /**
     * Object: 房地产进度计划 's 项目阶段 property 
     */
    public com.kingdee.eas.fdc.basedata.MeasureStageInfo getScheduleStage()
    {
        return (com.kingdee.eas.fdc.basedata.MeasureStageInfo)get("scheduleStage");
    }
    public void setScheduleStage(com.kingdee.eas.fdc.basedata.MeasureStageInfo item)
    {
        put("scheduleStage", item);
    }
    /**
     * Object:房地产进度计划's 影响程度property 
     */
    public com.kingdee.eas.fdc.schedule.RESchTaskEffectDegreeEnum getEffectDegree()
    {
        return com.kingdee.eas.fdc.schedule.RESchTaskEffectDegreeEnum.getEnum(getString("effectDegree"));
    }
    public void setEffectDegree(com.kingdee.eas.fdc.schedule.RESchTaskEffectDegreeEnum item)
    {
		if (item != null) {
        setString("effectDegree", item.getValue());
		}
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("D0FA7B86");
    }
}