package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractAgioBillInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractAgioBillInfo()
    {
        this("id");
    }
    protected AbstractAgioBillInfo(String pkField)
    {
        super(pkField);
        put("roomEntry", new com.kingdee.eas.fdc.sellhouse.AgioRoomEntryCollection());
    }
    /**
     * Object: 折扣 's 销售项目 property 
     */
    public com.kingdee.eas.fdc.sellhouse.SellProjectInfo getProject()
    {
        return (com.kingdee.eas.fdc.sellhouse.SellProjectInfo)get("project");
    }
    public void setProject(com.kingdee.eas.fdc.sellhouse.SellProjectInfo item)
    {
        put("project", item);
    }
    /**
     * Object:折扣's 计算方法property 
     */
    public com.kingdee.eas.fdc.sellhouse.AgioCalTypeEnum getCalType()
    {
        return com.kingdee.eas.fdc.sellhouse.AgioCalTypeEnum.getEnum(getString("calType"));
    }
    public void setCalType(com.kingdee.eas.fdc.sellhouse.AgioCalTypeEnum item)
    {
		if (item != null) {
        setString("calType", item.getValue());
		}
    }
    /**
     * Object:折扣's 百分比property 
     */
    public java.math.BigDecimal getPro()
    {
        return getBigDecimal("pro");
    }
    public void setPro(java.math.BigDecimal item)
    {
        setBigDecimal("pro", item);
    }
    /**
     * Object:折扣's 生效日期property 
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
     * Object:折扣's 失效日期property 
     */
    public java.util.Date getCancelDate()
    {
        return getDate("cancelDate");
    }
    public void setCancelDate(java.util.Date item)
    {
        setDate("cancelDate", item);
    }
    /**
     * Object:折扣's 是否特殊折扣property 
     */
    public boolean isIsEspecial()
    {
        return getBoolean("isEspecial");
    }
    public void setIsEspecial(boolean item)
    {
        setBoolean("isEspecial", item);
    }
    /**
     * Object: 折扣 's 指定房间列表 property 
     */
    public com.kingdee.eas.fdc.sellhouse.AgioRoomEntryCollection getRoomEntry()
    {
        return (com.kingdee.eas.fdc.sellhouse.AgioRoomEntryCollection)get("roomEntry");
    }
    /**
     * Object:折扣's 是否启用property 
     */
    public boolean isIsEnabled()
    {
        return getBoolean("isEnabled");
    }
    public void setIsEnabled(boolean item)
    {
        setBoolean("isEnabled", item);
    }
    /**
     * Object: 折扣 's 启用人 property 
     */
    public com.kingdee.eas.base.permission.UserInfo getEnabledMan()
    {
        return (com.kingdee.eas.base.permission.UserInfo)get("enabledMan");
    }
    public void setEnabledMan(com.kingdee.eas.base.permission.UserInfo item)
    {
        put("enabledMan", item);
    }
    /**
     * Object:折扣's 启用日期property 
     */
    public java.util.Date getEnabledDate()
    {
        return getDate("enabledDate");
    }
    public void setEnabledDate(java.util.Date item)
    {
        setDate("enabledDate", item);
    }
    /**
     * Object: 折扣 's 禁用人 property 
     */
    public com.kingdee.eas.base.permission.UserInfo getUnenableMan()
    {
        return (com.kingdee.eas.base.permission.UserInfo)get("unenableMan");
    }
    public void setUnenableMan(com.kingdee.eas.base.permission.UserInfo item)
    {
        put("unenableMan", item);
    }
    /**
     * Object:折扣's 禁用日期property 
     */
    public java.util.Date getUnenabledDate()
    {
        return getDate("unenabledDate");
    }
    public void setUnenabledDate(java.util.Date item)
    {
        setDate("unenabledDate", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("C93ED5CE");
    }
}