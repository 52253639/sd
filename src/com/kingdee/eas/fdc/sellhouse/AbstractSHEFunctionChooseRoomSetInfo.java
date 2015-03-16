package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractSHEFunctionChooseRoomSetInfo extends com.kingdee.eas.framework.CoreBaseInfo implements Serializable 
{
    public AbstractSHEFunctionChooseRoomSetInfo()
    {
        this("id");
    }
    protected AbstractSHEFunctionChooseRoomSetInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 售楼设置-选房设置 's 头 property 
     */
    public com.kingdee.eas.fdc.sellhouse.SHEFunctionSetInfo getHead()
    {
        return (com.kingdee.eas.fdc.sellhouse.SHEFunctionSetInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.sellhouse.SHEFunctionSetInfo item)
    {
        put("head", item);
    }
    /**
     * Object: 售楼设置-选房设置 's 项目 property 
     */
    public com.kingdee.eas.fdc.sellhouse.SellProjectInfo getSellProject()
    {
        return (com.kingdee.eas.fdc.sellhouse.SellProjectInfo)get("sellProject");
    }
    public void setSellProject(com.kingdee.eas.fdc.sellhouse.SellProjectInfo item)
    {
        put("sellProject", item);
    }
    /**
     * Object: 售楼设置-选房设置 's 楼栋 property 
     */
    public com.kingdee.eas.fdc.sellhouse.BuildingInfo getBuilding()
    {
        return (com.kingdee.eas.fdc.sellhouse.BuildingInfo)get("building");
    }
    public void setBuilding(com.kingdee.eas.fdc.sellhouse.BuildingInfo item)
    {
        put("building", item);
    }
    /**
     * Object:售楼设置-选房设置's 开始日期property 
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
     * Object:售楼设置-选房设置's 结束日期property 
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
     * Object:售楼设置-选房设置's 是否启用property 
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
     * Object:售楼设置-选房设置's 默认时效property 
     */
    public int getDefaultLimitDate()
    {
        return getInt("defaultLimitDate");
    }
    public void setDefaultLimitDate(int item)
    {
        setInt("defaultLimitDate", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("39F9A183");
    }
}