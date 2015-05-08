package com.kingdee.eas.fdc.schedule;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractFDCScheduleTaskInfo extends com.kingdee.eas.fdc.schedule.framework.ScheduleTaskBaseInfo implements Serializable 
{
    public AbstractFDCScheduleTaskInfo()
    {
        this("id");
    }
    protected AbstractFDCScheduleTaskInfo(String pkField)
    {
        super(pkField);
        put("helpPersonEntry", new com.kingdee.eas.fdc.schedule.HelpPersonEntryCollection());
        put("dependEntrys", new com.kingdee.eas.fdc.schedule.FDCScheduleTaskDependCollection());
        put("helpDeptEntry", new com.kingdee.eas.fdc.schedule.HelpDeptEntryCollection());
        put("bizType", new com.kingdee.eas.fdc.schedule.ScheduleTaskBizTypeCollection());
    }
    /**
     * Object: 房地产进度任务 's 后置任务 property 
     */
    public com.kingdee.eas.fdc.schedule.FDCScheduleTaskDependCollection getDependEntrys()
    {
        return (com.kingdee.eas.fdc.schedule.FDCScheduleTaskDependCollection)get("dependEntrys");
    }
    /**
     * Object: 房地产进度任务 's 上级任务 property 
     */
    public com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo getParent()
    {
        return (com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo item)
    {
        put("parent", item);
    }
    /**
     * Object: 房地产进度任务 's 进度 property 
     */
    public com.kingdee.eas.fdc.schedule.FDCScheduleInfo getSchedule()
    {
        return (com.kingdee.eas.fdc.schedule.FDCScheduleInfo)get("schedule");
    }
    public void setSchedule(com.kingdee.eas.fdc.schedule.FDCScheduleInfo item)
    {
        put("schedule", item);
    }
    /**
     * Object: 房地产进度任务 's 责任人 property 
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
     * Object: 房地产进度任务 's 责任部门 property 
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
     * Object: 房地产进度任务 's 计划编制部门 property 
     */
    public com.kingdee.eas.basedata.org.FullOrgUnitInfo getPlanDept()
    {
        return (com.kingdee.eas.basedata.org.FullOrgUnitInfo)get("planDept");
    }
    public void setPlanDept(com.kingdee.eas.basedata.org.FullOrgUnitInfo item)
    {
        put("planDept", item);
    }
    /**
     * Object:房地产进度任务's 有效工期property 
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
     * Object:房地产进度任务's 自然工期property 
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
     * Object:房地产进度任务's 关键任务property 
     */
    public boolean isIsKey()
    {
        return getBoolean("isKey");
    }
    public void setIsKey(boolean item)
    {
        setBoolean("isKey", item);
    }
    /**
     * Object: 房地产进度任务 's WBS节点 property 
     */
    public com.kingdee.eas.fdc.schedule.FDCWBSInfo getWbs()
    {
        return (com.kingdee.eas.fdc.schedule.FDCWBSInfo)get("wbs");
    }
    public void setWbs(com.kingdee.eas.fdc.schedule.FDCWBSInfo item)
    {
        put("wbs", item);
    }
    /**
     * Object:房地产进度任务's 实际完工日期property 
     */
    public java.util.Date getActualEndDate()
    {
        return getDate("actualEndDate");
    }
    public void setActualEndDate(java.util.Date item)
    {
        setDate("actualEndDate", item);
    }
    /**
     * Object: 房地产进度任务 's 管理人 property 
     */
    public com.kingdee.eas.basedata.person.PersonInfo getAdministrator()
    {
        return (com.kingdee.eas.basedata.person.PersonInfo)get("administrator");
    }
    public void setAdministrator(com.kingdee.eas.basedata.person.PersonInfo item)
    {
        put("administrator", item);
    }
    /**
     * Object: 房地产进度任务 's 知会人 property 
     */
    public com.kingdee.eas.basedata.person.PersonInfo getNoter()
    {
        return (com.kingdee.eas.basedata.person.PersonInfo)get("noter");
    }
    public void setNoter(com.kingdee.eas.basedata.person.PersonInfo item)
    {
        put("noter", item);
    }
    /**
     * Object: 房地产进度任务 's 责任部门经理 property 
     */
    public com.kingdee.eas.basedata.person.PersonInfo getManager()
    {
        return (com.kingdee.eas.basedata.person.PersonInfo)get("manager");
    }
    public void setManager(com.kingdee.eas.basedata.person.PersonInfo item)
    {
        put("manager", item);
    }
    /**
     * Object:房地产进度任务's 工程量property 
     */
    public java.math.BigDecimal getWorkLoad()
    {
        return getBigDecimal("workLoad");
    }
    public void setWorkLoad(java.math.BigDecimal item)
    {
        setBigDecimal("workLoad", item);
    }
    /**
     * Object:房地产进度任务's 是否引用property 
     */
    public boolean isIsRefer()
    {
        return getBoolean("isRefer");
    }
    public void setIsRefer(boolean item)
    {
        setBoolean("isRefer", item);
    }
    /**
     * Object: 房地产进度任务 's 风险负责人 property 
     */
    public com.kingdee.eas.basedata.person.PersonInfo getRiskResPerson()
    {
        return (com.kingdee.eas.basedata.person.PersonInfo)get("riskResPerson");
    }
    public void setRiskResPerson(com.kingdee.eas.basedata.person.PersonInfo item)
    {
        put("riskResPerson", item);
    }
    /**
     * Object:房地产进度任务's 里程碑状态property 
     */
    public com.kingdee.eas.fdc.schedule.MileStoneStatusEnum getMileStoneStatus()
    {
        return com.kingdee.eas.fdc.schedule.MileStoneStatusEnum.getEnum(getString("mileStoneStatus"));
    }
    public void setMileStoneStatus(com.kingdee.eas.fdc.schedule.MileStoneStatusEnum item)
    {
		if (item != null) {
        setString("mileStoneStatus", item.getValue());
		}
    }
    /**
     * Object:房地产进度任务's 实际开始时间property 
     */
    public java.util.Date getActualStartDate()
    {
        return getDate("actualStartDate");
    }
    public void setActualStartDate(java.util.Date item)
    {
        setDate("actualStartDate", item);
    }
    /**
     * Object: 房地产进度任务 's 业务类型 property 
     */
    public com.kingdee.eas.fdc.schedule.ScheduleTaskBizTypeCollection getBizType()
    {
        return (com.kingdee.eas.fdc.schedule.ScheduleTaskBizTypeCollection)get("bizType");
    }
    /**
     * Object: 房地产进度任务 's 标准任务指引 property 
     */
    public com.kingdee.eas.fdc.schedule.StandardTaskGuideInfo getStandardTask()
    {
        return (com.kingdee.eas.fdc.schedule.StandardTaskGuideInfo)get("standardTask");
    }
    public void setStandardTask(com.kingdee.eas.fdc.schedule.StandardTaskGuideInfo item)
    {
        put("standardTask", item);
    }
    /**
     * Object:房地产进度任务's 任务类别property 
     */
    public com.kingdee.eas.fdc.schedule.RESchTaskTypeEnum getTaskType()
    {
        return com.kingdee.eas.fdc.schedule.RESchTaskTypeEnum.getEnum(getString("taskType"));
    }
    public void setTaskType(com.kingdee.eas.fdc.schedule.RESchTaskTypeEnum item)
    {
		if (item != null) {
        setString("taskType", item.getValue());
		}
    }
    /**
     * Object: 房地产进度任务 's 成果类别 property 
     */
    public com.kingdee.eas.fdc.schedule.AchievementTypeInfo getAchievementType()
    {
        return (com.kingdee.eas.fdc.schedule.AchievementTypeInfo)get("achievementType");
    }
    public void setAchievementType(com.kingdee.eas.fdc.schedule.AchievementTypeInfo item)
    {
        put("achievementType", item);
    }
    /**
     * Object:房地产进度任务's 是否考核节点property 
     */
    public boolean isIsCheckNode()
    {
        return getBoolean("isCheckNode");
    }
    public void setIsCheckNode(boolean item)
    {
        setBoolean("isCheckNode", item);
    }
    /**
     * Object:房地产进度任务's 考核日期property 
     */
    public java.util.Date getCheckDate()
    {
        return getDate("checkDate");
    }
    public void setCheckDate(java.util.Date item)
    {
        setDate("checkDate", item);
    }
    /**
     * Object: 房地产进度任务 's 进度评价人 property 
     */
    public com.kingdee.eas.basedata.person.PersonInfo getPlanEvaluatePerson()
    {
        return (com.kingdee.eas.basedata.person.PersonInfo)get("planEvaluatePerson");
    }
    public void setPlanEvaluatePerson(com.kingdee.eas.basedata.person.PersonInfo item)
    {
        put("planEvaluatePerson", item);
    }
    /**
     * Object: 房地产进度任务 's 质量评价人 property 
     */
    public com.kingdee.eas.basedata.person.PersonInfo getQualityEvaluatePerson()
    {
        return (com.kingdee.eas.basedata.person.PersonInfo)get("qualityEvaluatePerson");
    }
    public void setQualityEvaluatePerson(com.kingdee.eas.basedata.person.PersonInfo item)
    {
        put("qualityEvaluatePerson", item);
    }
    /**
     * Object: 房地产进度任务 's 协助人 property 
     */
    public com.kingdee.eas.basedata.person.PersonInfo getHelpPerson()
    {
        return (com.kingdee.eas.basedata.person.PersonInfo)get("helpPerson");
    }
    public void setHelpPerson(com.kingdee.eas.basedata.person.PersonInfo item)
    {
        put("helpPerson", item);
    }
    /**
     * Object: 房地产进度任务 's 协助部门 property 
     */
    public com.kingdee.eas.basedata.org.FullOrgUnitInfo getHelpDept()
    {
        return (com.kingdee.eas.basedata.org.FullOrgUnitInfo)get("helpDept");
    }
    public void setHelpDept(com.kingdee.eas.basedata.org.FullOrgUnitInfo item)
    {
        put("helpDept", item);
    }
    /**
     * Object: 房地产进度任务 's 任务日历 property 
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
     * Object: 房地产进度任务 's 关联的主项任务 property 
     */
    public com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo getDependMainTaskID()
    {
        return (com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo)get("dependMainTaskID");
    }
    public void setDependMainTaskID(com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo item)
    {
        put("dependMainTaskID", item);
    }
    /**
     * Object:房地产进度任务's 源任务IDproperty 
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
     * Object:房地产进度任务's 预计完成日期property 
     */
    public java.util.Date getIntendEndDate()
    {
        return getDate("intendEndDate");
    }
    public void setIntendEndDate(java.util.Date item)
    {
        setDate("intendEndDate", item);
    }
    /**
     * Object: 房地产进度任务 's 所属专项 property 
     */
    public com.kingdee.eas.fdc.schedule.ProjectSpecialInfo getBelongToSpecial()
    {
        return (com.kingdee.eas.fdc.schedule.ProjectSpecialInfo)get("belongToSpecial");
    }
    public void setBelongToSpecial(com.kingdee.eas.fdc.schedule.ProjectSpecialInfo item)
    {
        put("belongToSpecial", item);
    }
    /**
     * Object: 房地产进度任务 's 模板任务 property 
     */
    public com.kingdee.eas.fdc.schedule.RESchTemplateTaskInfo getSchTemplateTask()
    {
        return (com.kingdee.eas.fdc.schedule.RESchTemplateTaskInfo)get("schTemplateTask");
    }
    public void setSchTemplateTask(com.kingdee.eas.fdc.schedule.RESchTemplateTaskInfo item)
    {
        put("schTemplateTask", item);
    }
    /**
     * Object: 房地产进度任务 's 考核节点 property 
     */
    public com.kingdee.eas.fdc.schedule.CheckNodeInfo getCheckNode()
    {
        return (com.kingdee.eas.fdc.schedule.CheckNodeInfo)get("checkNode");
    }
    public void setCheckNode(com.kingdee.eas.fdc.schedule.CheckNodeInfo item)
    {
        put("checkNode", item);
    }
    /**
     * Object: 房地产进度任务 's 协助人（多选） property 
     */
    public com.kingdee.eas.fdc.schedule.HelpPersonEntryCollection getHelpPersonEntry()
    {
        return (com.kingdee.eas.fdc.schedule.HelpPersonEntryCollection)get("helpPersonEntry");
    }
    /**
     * Object: 房地产进度任务 's 协助部门（多选） property 
     */
    public com.kingdee.eas.fdc.schedule.HelpDeptEntryCollection getHelpDeptEntry()
    {
        return (com.kingdee.eas.fdc.schedule.HelpDeptEntryCollection)get("helpDeptEntry");
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("90CCF22B");
    }
}