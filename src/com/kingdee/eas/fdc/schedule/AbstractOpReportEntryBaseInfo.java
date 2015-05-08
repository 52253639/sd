package com.kingdee.eas.fdc.schedule;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractOpReportEntryBaseInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractOpReportEntryBaseInfo()
    {
        this("taskName");
    }
    protected AbstractOpReportEntryBaseInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:运营报告分录's 任务名称property 
     */
    public String getTaskName()
    {
        return getTaskName((Locale)null);
    }
    public void setTaskName(String item)
    {
		setTaskName(item,(Locale)null);
    }
    public String getTaskName(Locale local)
    {
        return TypeConversionUtils.objToString(get("taskName", local));
    }
    public void setTaskName(String item, Locale local)
    {
        put("taskName", item, local);
    }
    /**
     * Object:运营报告分录's 计划开始日期（实际开始日期借用该字段）property 
     */
    public java.util.Date getPlanStartDate()
    {
        return getDate("planStartDate");
    }
    public void setPlanStartDate(java.util.Date item)
    {
        setDate("planStartDate", item);
    }
    /**
     * Object:运营报告分录's 计划完成日期property 
     */
    public java.util.Date getPlanEndDate()
    {
        return getDate("planEndDate");
    }
    public void setPlanEndDate(java.util.Date item)
    {
        setDate("planEndDate", item);
    }
    /**
     * Object:运营报告分录's 实际完成日期property 
     */
    public java.util.Date getRealEndDate()
    {
        return getDate("realEndDate");
    }
    public void setRealEndDate(java.util.Date item)
    {
        setDate("realEndDate", item);
    }
    /**
     * Object:运营报告分录's 预计完成日期property 
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
     * Object:运营报告分录's 是否完成property 
     */
    public boolean isIsComplete()
    {
        return getBoolean("isComplete");
    }
    public void setIsComplete(boolean item)
    {
        setBoolean("isComplete", item);
    }
    /**
     * Object:运营报告分录's 完成进度(%)property 
     */
    public int getCompletePrecent()
    {
        return getInt("completePrecent");
    }
    public void setCompletePrecent(int item)
    {
        setInt("completePrecent", item);
    }
    /**
     * Object:运营报告分录's 本次完工工程量property 
     */
    public java.math.BigDecimal getCompleteAmount()
    {
        return getBigDecimal("completeAmount");
    }
    public void setCompleteAmount(java.math.BigDecimal item)
    {
        setBigDecimal("completeAmount", item);
    }
    /**
     * Object:运营报告分录's 完成情况说明property 
     */
    public String getDescription()
    {
        return getDescription((Locale)null);
    }
    public void setDescription(String item)
    {
		setDescription(item,(Locale)null);
    }
    public String getDescription(Locale local)
    {
        return TypeConversionUtils.objToString(get("description", local));
    }
    public void setDescription(String item, Locale local)
    {
        put("description", item, local);
    }
    /**
     * Object:运营报告分录's 是否下周/月任务property 
     */
    public boolean isIsNext()
    {
        return getBoolean("isNext");
    }
    public void setIsNext(boolean item)
    {
        setBoolean("isNext", item);
    }
    /**
     * Object: 运营报告分录 's 相关任务 property 
     */
    public com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo getRelateTask()
    {
        return (com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo)get("relateTask");
    }
    public void setRelateTask(com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo item)
    {
        put("relateTask", item);
    }
    /**
     * Object: 运营报告分录 's 责任人 property 
     */
    public com.kingdee.eas.basedata.person.PersonInfo getRespPerson()
    {
        return (com.kingdee.eas.basedata.person.PersonInfo)get("respPerson");
    }
    public void setRespPerson(com.kingdee.eas.basedata.person.PersonInfo item)
    {
        put("respPerson", item);
    }
    /**
     * Object: 运营报告分录 's 责任部门 property 
     */
    public com.kingdee.eas.basedata.org.FullOrgUnitInfo getRespDept()
    {
        return (com.kingdee.eas.basedata.org.FullOrgUnitInfo)get("respDept");
    }
    public void setRespDept(com.kingdee.eas.basedata.org.FullOrgUnitInfo item)
    {
        put("respDept", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("C966A658");
    }
}