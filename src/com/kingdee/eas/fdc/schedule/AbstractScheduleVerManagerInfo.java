package com.kingdee.eas.fdc.schedule;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractScheduleVerManagerInfo extends com.kingdee.eas.framework.BillBaseInfo implements Serializable 
{
    public AbstractScheduleVerManagerInfo()
    {
        this("id");
    }
    protected AbstractScheduleVerManagerInfo(String pkField)
    {
        super(pkField);
        put("entrys", new com.kingdee.eas.fdc.schedule.ScheduleVerManagerEntryCollection());
    }
    /**
     * Object:进度计划版本管理's 版本property 
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
     * Object: 进度计划版本管理 's 工程项目 property 
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
     * Object:进度计划版本管理's 计划有效工期property 
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
     * Object:进度计划版本管理's 计划自然工期property 
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
     * Object:进度计划版本管理's 开始时间property 
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
     * Object:进度计划版本管理's 结束时间property 
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
     * Object:进度计划版本管理's 版本形成原因property 
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
     * Object:进度计划版本管理's 最新版本property 
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
     * Object: 进度计划版本管理 's 分录 property 
     */
    public com.kingdee.eas.fdc.schedule.ScheduleVerManagerEntryCollection getEntrys()
    {
        return (com.kingdee.eas.fdc.schedule.ScheduleVerManagerEntryCollection)get("entrys");
    }
    /**
     * Object:进度计划版本管理's 状态property 
     */
    public com.kingdee.eas.fdc.schedule.framework.ScheduleStateEnum getState()
    {
        return com.kingdee.eas.fdc.schedule.framework.ScheduleStateEnum.getEnum(getString("state"));
    }
    public void setState(com.kingdee.eas.fdc.schedule.framework.ScheduleStateEnum item)
    {
		if (item != null) {
        setString("state", item.getValue());
		}
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("3FA212D7");
    }
}