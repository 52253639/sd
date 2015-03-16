package com.kingdee.eas.fdc.tenancy;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractRestReceivableEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractRestReceivableEntryInfo()
    {
        this("id");
    }
    protected AbstractRestReceivableEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 分录 's 单据头 property 
     */
    public com.kingdee.eas.fdc.tenancy.RestReceivableInfo getParent()
    {
        return (com.kingdee.eas.fdc.tenancy.RestReceivableInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.tenancy.RestReceivableInfo item)
    {
        put("parent", item);
    }
    /**
     * Object:分录's 应收日期property 
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
     * Object:分录's 应收金额property 
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
     * Object: 分录 's 币别 property 
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
     * Object:分录's 实收日期property 
     */
    public java.util.Date getActRevDate()
    {
        return getDate("actRevDate");
    }
    public void setActRevDate(java.util.Date item)
    {
        setDate("actRevDate", item);
    }
    /**
     * Object:分录's 实收金额property 
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
     * Object: 分录 's 款项名称 property 
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
     * Object: 分录 's 租赁合同的合同条款 property 
     */
    public com.kingdee.eas.fdc.tenancy.TenBillOtherPayInfo getTenancyBillEntry()
    {
        return (com.kingdee.eas.fdc.tenancy.TenBillOtherPayInfo)get("tenancyBillEntry");
    }
    public void setTenancyBillEntry(com.kingdee.eas.fdc.tenancy.TenBillOtherPayInfo item)
    {
        put("tenancyBillEntry", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("3BC2CCF9");
    }
}