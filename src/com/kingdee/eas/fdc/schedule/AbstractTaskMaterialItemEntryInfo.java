package com.kingdee.eas.fdc.schedule;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractTaskMaterialItemEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractTaskMaterialItemEntryInfo()
    {
        this("id");
    }
    protected AbstractTaskMaterialItemEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:物资事件分录's 状态property 
     */
    public com.kingdee.eas.fdc.basedata.FDCBillStateEnum getState()
    {
        return com.kingdee.eas.fdc.basedata.FDCBillStateEnum.getEnum(getString("state"));
    }
    public void setState(com.kingdee.eas.fdc.basedata.FDCBillStateEnum item)
    {
		if (item != null) {
        setString("state", item.getValue());
		}
    }
    /**
     * Object:物资事件分录's 发生时间property 
     */
    public java.util.Date getHappenTime()
    {
        return getDate("happenTime");
    }
    public void setHappenTime(java.util.Date item)
    {
        setDate("happenTime", item);
    }
    /**
     * Object:物资事件分录's 标题property 
     */
    public String getTitle()
    {
        return getString("title");
    }
    public void setTitle(String item)
    {
        setString("title", item);
    }
    /**
     * Object: 物资事件分录 's 表头 property 
     */
    public com.kingdee.eas.fdc.schedule.TaskMaterialPlanInfo getParent()
    {
        return (com.kingdee.eas.fdc.schedule.TaskMaterialPlanInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.schedule.TaskMaterialPlanInfo item)
    {
        put("parent", item);
    }
    /**
     * Object:物资事件分录's 评分（十分制）property 
     */
    public java.math.BigDecimal getAppraise()
    {
        return getBigDecimal("appraise");
    }
    public void setAppraise(java.math.BigDecimal item)
    {
        setBigDecimal("appraise", item);
    }
    /**
     * Object:物资事件分录's 描述property 
     */
    public String getDescription()
    {
        return getString("description");
    }
    public void setDescription(String item)
    {
        setString("description", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("F8B4029D");
    }
}