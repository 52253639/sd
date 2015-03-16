package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractPurchaseChangePayListOldEntryInfo extends com.kingdee.eas.framework.DataBaseInfo implements Serializable 
{
    public AbstractPurchaseChangePayListOldEntryInfo()
    {
        this("id");
    }
    protected AbstractPurchaseChangePayListOldEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 认购变更单老的付款信息 's 认购变更单 property 
     */
    public com.kingdee.eas.fdc.sellhouse.PurchaseChangeInfo getPurchaseChange()
    {
        return (com.kingdee.eas.fdc.sellhouse.PurchaseChangeInfo)get("purchaseChange");
    }
    public void setPurchaseChange(com.kingdee.eas.fdc.sellhouse.PurchaseChangeInfo item)
    {
        put("purchaseChange", item);
    }
    /**
     * Object: 认购变更单老的付款信息 's 币别 property 
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
     * Object: 认购变更单老的付款信息 's 款项定义 property 
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
     * Object:认购变更单老的付款信息's 序号property 
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
     * Object:认购变更单老的付款信息's 应付日期property 
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
     * Object:认购变更单老的付款信息's 应付金额property 
     */
    public java.math.BigDecimal getAppAmount()
    {
        return getBigDecimal("appAmount");
    }
    public void setAppAmount(java.math.BigDecimal item)
    {
        setBigDecimal("appAmount", item);
    }
    /**
     * Object:认购变更单老的付款信息's 实付金额property 
     */
    public java.math.BigDecimal getActRevAmount()
    {
        return getBigDecimal("actRevAmount");
    }
    public void setActRevAmount(java.math.BigDecimal item)
    {
        setBigDecimal("actRevAmount", item);
    }
    /**
     * Object:认购变更单老的付款信息's 分期property 
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
     * Object:认购变更单老的付款信息's 间隔月数property 
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
     * Object:认购变更单老的付款信息's 每月付款日property 
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
     * Object:认购变更单老的付款信息's 实付日期property 
     */
    public java.util.Date getActRevDate()
    {
        return getDate("actRevDate");
    }
    public void setActRevDate(java.util.Date item)
    {
        setDate("actRevDate", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("223D28C5");
    }
}