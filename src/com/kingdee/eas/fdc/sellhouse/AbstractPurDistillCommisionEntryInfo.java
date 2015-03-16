package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractPurDistillCommisionEntryInfo extends com.kingdee.eas.framework.CoreBaseInfo implements Serializable 
{
    public AbstractPurDistillCommisionEntryInfo()
    {
        this("id");
    }
    protected AbstractPurDistillCommisionEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 认购单提佣分录 's 认购单 property 
     */
    public com.kingdee.eas.fdc.sellhouse.PurchaseInfo getHead()
    {
        return (com.kingdee.eas.fdc.sellhouse.PurchaseInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.sellhouse.PurchaseInfo item)
    {
        put("head", item);
    }
    /**
     * Object: 认购单提佣分录 's 分单人员 property 
     */
    public com.kingdee.eas.base.permission.UserInfo getUser()
    {
        return (com.kingdee.eas.base.permission.UserInfo)get("user");
    }
    public void setUser(com.kingdee.eas.base.permission.UserInfo item)
    {
        put("user", item);
    }
    /**
     * Object:认购单提佣分录's 计提比例property 
     */
    public java.math.BigDecimal getSharePercent()
    {
        return getBigDecimal("sharePercent");
    }
    public void setSharePercent(java.math.BigDecimal item)
    {
        setBigDecimal("sharePercent", item);
    }
    /**
     * Object:认购单提佣分录's 分单比例property 
     */
    public java.math.BigDecimal getTakePercentage()
    {
        return getBigDecimal("takePercentage");
    }
    public void setTakePercentage(java.math.BigDecimal item)
    {
        setBigDecimal("takePercentage", item);
    }
    /**
     * Object:认购单提佣分录's 提佣级次property 
     */
    public int getDistillCommisionLevel()
    {
        return getInt("distillCommisionLevel");
    }
    public void setDistillCommisionLevel(int item)
    {
        setInt("distillCommisionLevel", item);
    }
    /**
     * Object: 认购单提佣分录 's 间接提佣分组 property 
     */
    public com.kingdee.eas.fdc.tenancy.MarketingUnitInfo getMarketUnit()
    {
        return (com.kingdee.eas.fdc.tenancy.MarketingUnitInfo)get("marketUnit");
    }
    public void setMarketUnit(com.kingdee.eas.fdc.tenancy.MarketingUnitInfo item)
    {
        put("marketUnit", item);
    }
    /**
     * Object:认购单提佣分录's 是否完成提佣property 
     */
    public boolean isIsAchieveCommision()
    {
        return getBoolean("isAchieveCommision");
    }
    public void setIsAchieveCommision(boolean item)
    {
        setBoolean("isAchieveCommision", item);
    }
    /**
     * Object:认购单提佣分录's 已计提金额property 
     */
    public java.math.BigDecimal getAlreadyShareAmount()
    {
        return getBigDecimal("alreadyShareAmount");
    }
    public void setAlreadyShareAmount(java.math.BigDecimal item)
    {
        setBigDecimal("alreadyShareAmount", item);
    }
    /**
     * Object:认购单提佣分录's 接手时间property 
     */
    public java.util.Date getHandleTime()
    {
        return getDate("handleTime");
    }
    public void setHandleTime(java.util.Date item)
    {
        setDate("handleTime", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("1E119071");
    }
}