package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractPayListEntryInfo extends com.kingdee.eas.framework.DataBaseInfo implements Serializable 
{
    public AbstractPayListEntryInfo()
    {
        this("id");
    }
    protected AbstractPayListEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 付款明细分录 's 付款类型头 property 
     */
    public com.kingdee.eas.fdc.sellhouse.SHEPayTypeInfo getHead()
    {
        return (com.kingdee.eas.fdc.sellhouse.SHEPayTypeInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.sellhouse.SHEPayTypeInfo item)
    {
        put("head", item);
    }
    /**
     * Object:付款明细分录's 序号property 
     */
    public int getSeq()
    {
        return getInt("seq");
    }
    public void setSeq(int item)
    {
        setInt("seq", item);
    }
    /**
     * Object:付款明细分录's 付款时间类型property 
     */
    public com.kingdee.eas.fdc.sellhouse.BizTimeEnum getBizTime()
    {
        return com.kingdee.eas.fdc.sellhouse.BizTimeEnum.getEnum(getString("bizTime"));
    }
    public void setBizTime(com.kingdee.eas.fdc.sellhouse.BizTimeEnum item)
    {
		if (item != null) {
        setString("bizTime", item.getValue());
		}
    }
    /**
     * Object:付款明细分录's 月内 property 
     */
    public int getMonthLimit()
    {
        return getInt("monthLimit");
    }
    public void setMonthLimit(int item)
    {
        setInt("monthLimit", item);
    }
    /**
     * Object:付款明细分录's 日内property 
     */
    public int getDayLimit()
    {
        return getInt("dayLimit");
    }
    public void setDayLimit(int item)
    {
        setInt("dayLimit", item);
    }
    /**
     * Object:付款明细分录's 指定日期property 
     */
    public java.util.Date getAppDate()
    {
        return getDate("appDate");
    }
    public void setAppDate(java.util.Date item)
    {
        setDate("appDate", item);
    }
    /**
     * Object:付款明细分录's 是否款项property 
     */
    public boolean isIsMoney()
    {
        return getBoolean("isMoney");
    }
    public void setIsMoney(boolean item)
    {
        setBoolean("isMoney", item);
    }
    /**
     * Object: 付款明细分录 's 款项定义 property 
     */
    public com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo getMoneyDefine()
    {
        return (com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo)get("moneyDefine");
    }
    public void setMoneyDefine(com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo item)
    {
        put("moneyDefine", item);
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
     * Object:付款明细分录's 金额property 
     */
    public java.math.BigDecimal getValue()
    {
        return getBigDecimal("value");
    }
    public void setValue(java.math.BigDecimal item)
    {
        setBigDecimal("value", item);
    }
    /**
     * Object:付款明细分录's 比例property 
     */
    public java.math.BigDecimal getProportion()
    {
        return getBigDecimal("proportion");
    }
    public void setProportion(java.math.BigDecimal item)
    {
        setBigDecimal("proportion", item);
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
     * Object:付款明细分录's 是否扣除定金property 
     */
    public boolean isIsDeductEarnestMoney()
    {
        return getBoolean("isDeductEarnestMoney");
    }
    public void setIsDeductEarnestMoney(boolean item)
    {
        setBoolean("isDeductEarnestMoney", item);
    }
    /**
     * Object: 付款明细分录 's 代收费用定义 property 
     */
    public com.kingdee.eas.fdc.sellhouse.CollectionInfo getCollection()
    {
        return (com.kingdee.eas.fdc.sellhouse.CollectionInfo)get("collection");
    }
    public void setCollection(com.kingdee.eas.fdc.sellhouse.CollectionInfo item)
    {
        put("collection", item);
    }
    /**
     * Object:付款明细分录's 参照时间property 
     */
    public com.kingdee.eas.fdc.sellhouse.SHEPayTypeBizTimeEnum getPayTypeBizTime()
    {
        return com.kingdee.eas.fdc.sellhouse.SHEPayTypeBizTimeEnum.getEnum(getString("payTypeBizTime"));
    }
    public void setPayTypeBizTime(com.kingdee.eas.fdc.sellhouse.SHEPayTypeBizTimeEnum item)
    {
		if (item != null) {
        setString("payTypeBizTime", item.getValue());
		}
    }
    /**
     * Object:付款明细分录's 签合同书时间property 
     */
    public java.util.Date getSigncontracttime()
    {
        return getDate("signcontracttime");
    }
    public void setSigncontracttime(java.util.Date item)
    {
        setDate("signcontracttime", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("A46CBD07");
    }
}