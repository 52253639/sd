package com.kingdee.eas.fdc.market;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractMovementPlanEntryInfo extends com.kingdee.eas.framework.BillEntryBaseInfo implements Serializable 
{
    public AbstractMovementPlanEntryInfo()
    {
        this("id");
    }
    protected AbstractMovementPlanEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 分录 's 单据头 property 
     */
    public com.kingdee.eas.fdc.market.MovementPlanInfo getParent()
    {
        return (com.kingdee.eas.fdc.market.MovementPlanInfo)get("Parent");
    }
    public void setParent(com.kingdee.eas.fdc.market.MovementPlanInfo item)
    {
        put("Parent", item);
    }
    /**
     * Object:分录's 预计费用property 
     */
    public java.math.BigDecimal getPredictCost()
    {
        return getBigDecimal("predictCost");
    }
    public void setPredictCost(java.math.BigDecimal item)
    {
        setBigDecimal("predictCost", item);
    }
    /**
     * Object:分录's 预计来电property 
     */
    public int getPredictCall()
    {
        return getInt("predictCall");
    }
    public void setPredictCall(int item)
    {
        setInt("predictCall", item);
    }
    /**
     * Object:分录's 预计来访property 
     */
    public int getPredictVisit()
    {
        return getInt("predictVisit");
    }
    public void setPredictVisit(int item)
    {
        setInt("predictVisit", item);
    }
    /**
     * Object:分录's 预计成交property 
     */
    public int getPredictBargain()
    {
        return getInt("predictBargain");
    }
    public void setPredictBargain(int item)
    {
        setInt("predictBargain", item);
    }
    /**
     * Object:分录's 主题及内容property 
     */
    public String getSubject()
    {
        return getString("subject");
    }
    public void setSubject(String item)
    {
        setString("subject", item);
    }
    /**
     * Object:分录's 备注property 
     */
    public String getRemark()
    {
        return getString("remark");
    }
    public void setRemark(String item)
    {
        setString("remark", item);
    }
    /**
     * Object:分录's 折扣property 
     */
    public String getDiscount()
    {
        return getString("discount");
    }
    public void setDiscount(String item)
    {
        setString("discount", item);
    }
    /**
     * Object:分录's 详细时间property 
     */
    public String getActiveTime()
    {
        return getString("activeTime");
    }
    public void setActiveTime(String item)
    {
        setString("activeTime", item);
    }
    /**
     * Object: 分录 's 媒体 property 
     */
    public com.kingdee.eas.fdc.market.MediaInfo getMedia()
    {
        return (com.kingdee.eas.fdc.market.MediaInfo)get("media");
    }
    public void setMedia(com.kingdee.eas.fdc.market.MediaInfo item)
    {
        put("media", item);
    }
    /**
     * Object:分录's 开始时间property 
     */
    public java.util.Date getStartTime()
    {
        return getDate("startTime");
    }
    public void setStartTime(java.util.Date item)
    {
        setDate("startTime", item);
    }
    /**
     * Object:分录's 结束时间property 
     */
    public java.util.Date getEndTime()
    {
        return getDate("endTime");
    }
    public void setEndTime(java.util.Date item)
    {
        setDate("endTime", item);
    }
    /**
     * Object: 分录 's 活动类型 property 
     */
    public com.kingdee.eas.fdc.market.MarketTypeInfo getMmType()
    {
        return (com.kingdee.eas.fdc.market.MarketTypeInfo)get("mmType");
    }
    public void setMmType(com.kingdee.eas.fdc.market.MarketTypeInfo item)
    {
        put("mmType", item);
    }
    /**
     * Object:分录's 所属类型property 
     */
    public String getMmParent()
    {
        return getString("mmParent");
    }
    public void setMmParent(String item)
    {
        setString("mmParent", item);
    }
    /**
     * Object:分录's 选择property 
     */
    public boolean isIsSelect()
    {
        return getBoolean("isSelect");
    }
    public void setIsSelect(boolean item)
    {
        setBoolean("isSelect", item);
    }
    /**
     * Object:分录's 活动idproperty 
     */
    public String getMovementId()
    {
        return getString("movementId");
    }
    public void setMovementId(String item)
    {
        setString("movementId", item);
    }
    /**
     * Object: 分录 's 营销项目 property 
     */
    public com.kingdee.eas.fdc.sellhouse.SellProjectInfo getSellProject()
    {
        return (com.kingdee.eas.fdc.sellhouse.SellProjectInfo)get("sellProject");
    }
    public void setSellProject(com.kingdee.eas.fdc.sellhouse.SellProjectInfo item)
    {
        put("sellProject", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("56F46929");
    }
}