package com.kingdee.eas.fdc.schedule;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractScheduleAdjustGattReqInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractScheduleAdjustGattReqInfo()
    {
        this("id");
    }
    protected AbstractScheduleAdjustGattReqInfo(String pkField)
    {
        super(pkField);
        put("entrys", new com.kingdee.eas.fdc.schedule.ScheduleAdjustGattReqWBSEntryCollection());
    }
    /**
     * Object:计划调整申请(甘特图模式)'s 调整类别property 
     */
    public com.kingdee.eas.fdc.schedule.ScheduleAdjustTypeEnum getAdjustType()
    {
        return com.kingdee.eas.fdc.schedule.ScheduleAdjustTypeEnum.getEnum(getString("adjustType"));
    }
    public void setAdjustType(com.kingdee.eas.fdc.schedule.ScheduleAdjustTypeEnum item)
    {
		if (item != null) {
        setString("adjustType", item.getValue());
		}
    }
    /**
     * Object:计划调整申请(甘特图模式)'s 影响程度property 
     */
    public com.kingdee.eas.fdc.schedule.ScheduleAffectEnum getAffect()
    {
        return com.kingdee.eas.fdc.schedule.ScheduleAffectEnum.getEnum(getString("affect"));
    }
    public void setAffect(com.kingdee.eas.fdc.schedule.ScheduleAffectEnum item)
    {
		if (item != null) {
        setString("affect", item.getValue());
		}
    }
    /**
     * Object:计划调整申请(甘特图模式)'s 发起日期property 
     */
    public java.util.Date getReqDate()
    {
        return getDate("reqDate");
    }
    public void setReqDate(java.util.Date item)
    {
        setDate("reqDate", item);
    }
    /**
     * Object:计划调整申请(甘特图模式)'s 影响工期property 
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
     * Object:计划调整申请(甘特图模式)'s 调整说明property 
     */
    public String getAdjustDesc()
    {
        return getString("adjustDesc");
    }
    public void setAdjustDesc(String item)
    {
        setString("adjustDesc", item);
    }
    /**
     * Object: 计划调整申请(甘特图模式) 's 工程项目 property 
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
     * Object: 计划调整申请(甘特图模式) 's 基准版本 property 
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
     * Object: 计划调整申请(甘特图模式) 's WBS影响分录 property 
     */
    public com.kingdee.eas.fdc.schedule.ScheduleAdjustGattReqWBSEntryCollection getEntrys()
    {
        return (com.kingdee.eas.fdc.schedule.ScheduleAdjustGattReqWBSEntryCollection)get("entrys");
    }
    /**
     * Object: 计划调整申请(甘特图模式) 's 调整后的版本 property 
     */
    public com.kingdee.eas.fdc.schedule.ScheduleVerManagerInfo getNewVer()
    {
        return (com.kingdee.eas.fdc.schedule.ScheduleVerManagerInfo)get("newVer");
    }
    public void setNewVer(com.kingdee.eas.fdc.schedule.ScheduleVerManagerInfo item)
    {
        put("newVer", item);
    }
    /**
     * Object: 计划调整申请(甘特图模式) 's 调整原因 property 
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
     * Object:计划调整申请(甘特图模式)'s 最大任务变动量property 
     */
    public java.math.BigDecimal getMaxTaskChange()
    {
        return getBigDecimal("maxTaskChange");
    }
    public void setMaxTaskChange(java.math.BigDecimal item)
    {
        setBigDecimal("maxTaskChange", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("6CDE9F68");
    }
}