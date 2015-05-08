package com.kingdee.eas.fdc.schedule;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractTaskLoadEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractTaskLoadEntryInfo()
    {
        this("id");
    }
    protected AbstractTaskLoadEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 进度任务工程量 's wbs property 
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
     * Object:进度任务工程量's 本次完工量property 
     */
    public java.math.BigDecimal getLoad()
    {
        return getBigDecimal("load");
    }
    public void setLoad(java.math.BigDecimal item)
    {
        setBigDecimal("load", item);
    }
    /**
     * Object:进度任务工程量's 本次完工百分比property 
     */
    public java.math.BigDecimal getPercent()
    {
        return getBigDecimal("percent");
    }
    public void setPercent(java.math.BigDecimal item)
    {
        setBigDecimal("percent", item);
    }
    /**
     * Object:进度任务工程量's 录入日期property 
     */
    public java.util.Date getCreateDate()
    {
        return getDate("createDate");
    }
    public void setCreateDate(java.util.Date item)
    {
        setDate("createDate", item);
    }
    /**
     * Object:进度任务工程量's 完工日期property 
     */
    public java.util.Date getCompleteDate()
    {
        return getDate("completeDate");
    }
    public void setCompleteDate(java.util.Date item)
    {
        setDate("completeDate", item);
    }
    /**
     * Object: 进度任务工程量 's 录入人 property 
     */
    public com.kingdee.eas.base.permission.UserInfo getCreator()
    {
        return (com.kingdee.eas.base.permission.UserInfo)get("creator");
    }
    public void setCreator(com.kingdee.eas.base.permission.UserInfo item)
    {
        put("creator", item);
    }
    /**
     * Object:进度任务工程量's 备注property 
     */
    public String getDesc()
    {
        return getString("desc");
    }
    public void setDesc(String item)
    {
        setString("desc", item);
    }
    /**
     * Object: 进度任务工程量 's 确认人 property 
     */
    public com.kingdee.eas.base.permission.UserInfo getConfirmer()
    {
        return (com.kingdee.eas.base.permission.UserInfo)get("confirmer");
    }
    public void setConfirmer(com.kingdee.eas.base.permission.UserInfo item)
    {
        put("confirmer", item);
    }
    /**
     * Object:进度任务工程量's 确认日期property 
     */
    public java.util.Date getConfirmDate()
    {
        return getDate("confirmDate");
    }
    public void setConfirmDate(java.util.Date item)
    {
        setDate("confirmDate", item);
    }
    /**
     * Object:进度任务工程量's 是否已确认property 
     */
    public boolean isIsConfirm()
    {
        return getBoolean("isConfirm");
    }
    public void setIsConfirm(boolean item)
    {
        setBoolean("isConfirm", item);
    }
    /**
     * Object:进度任务工程量's 引用单据idproperty 
     */
    public String getObjectId()
    {
        return getString("objectId");
    }
    public void setObjectId(String item)
    {
        setString("objectId", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("E71E0311");
    }
}