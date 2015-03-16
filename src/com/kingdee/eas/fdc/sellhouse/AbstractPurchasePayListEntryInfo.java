package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractPurchasePayListEntryInfo extends com.kingdee.eas.fdc.basecrm.RevListInfo implements Serializable 
{
    public AbstractPurchasePayListEntryInfo()
    {
        this("id");
    }
    protected AbstractPurchasePayListEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 付款明细分录 's 付款类型头 property 
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
     * Object: 付款明细分录 's 币别 property 
     */
    public com.kingdee.eas.basedata.assistant.CurrencyInfo getCurrency()
    {
        return (com.kingdee.eas.basedata.assistant.CurrencyInfo)get("currency");
    }
    public void setCurrency(com.kingdee.eas.basedata.assistant.CurrencyInfo item)
    {
        put("currency", item);
    }
    /**
     * Object:付款明细分录's 分期property 
     */
    public int getTerm()
    {
        return getInt("term");
    }
    public void setTerm(int item)
    {
        setInt("term", item);
    }
    /**
     * Object:付款明细分录's 间隔月数property 
     */
    public int getMonthInterval()
    {
        return getInt("monthInterval");
    }
    public void setMonthInterval(int item)
    {
        setInt("monthInterval", item);
    }
    /**
     * Object:付款明细分录's 每月付款日property 
     */
    public int getPayDateByMonth()
    {
        return getInt("payDateByMonth");
    }
    public void setPayDateByMonth(int item)
    {
        setInt("payDateByMonth", item);
    }
    /**
     * Object:付款明细分录's 更新时间property 
     */
    public java.sql.Timestamp getLastUpdateTime()
    {
        return getTimestamp("lastUpdateTime");
    }
    public void setLastUpdateTime(java.sql.Timestamp item)
    {
        setTimestamp("lastUpdateTime", item);
    }
    /**
     * Object:付款明细分录's 创建时间property 
     */
    public java.sql.Timestamp getCreateTime()
    {
        return getTimestamp("createTime");
    }
    public void setCreateTime(java.sql.Timestamp item)
    {
        setTimestamp("createTime", item);
    }
    /**
     * Object:付款明细分录's 描述property 
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
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("8B4211A8");
    }
}