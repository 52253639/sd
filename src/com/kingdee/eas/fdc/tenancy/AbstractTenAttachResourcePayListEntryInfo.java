package com.kingdee.eas.fdc.tenancy;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractTenAttachResourcePayListEntryInfo extends com.kingdee.eas.framework.CoreBaseInfo implements Serializable 
{
    public AbstractTenAttachResourcePayListEntryInfo()
    {
        this("id");
    }
    protected AbstractTenAttachResourcePayListEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 租赁配套资源付款明细分录 's 租赁配套资源 property 
     */
    public com.kingdee.eas.fdc.tenancy.TenAttachResourceEntryInfo getTenAttachRes()
    {
        return (com.kingdee.eas.fdc.tenancy.TenAttachResourceEntryInfo)get("tenAttachRes");
    }
    public void setTenAttachRes(com.kingdee.eas.fdc.tenancy.TenAttachResourceEntryInfo item)
    {
        put("tenAttachRes", item);
    }
    /**
     * Object: 租赁配套资源付款明细分录 's 币别 property 
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
     * Object: 租赁配套资源付款明细分录 's 款项类型 property 
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
     * Object:租赁配套资源付款明细分录's 应付金额property 
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
     * Object:租赁配套资源付款明细分录's 租期序号property 
     */
    public int getLeaseSeq()
    {
        return getInt("leaseSeq");
    }
    public void setLeaseSeq(int item)
    {
        setInt("leaseSeq", item);
    }
    /**
     * Object:租赁配套资源付款明细分录's 起始日期property 
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
     * Object:租赁配套资源付款明细分录's 结束日期property 
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
     * Object:租赁配套资源付款明细分录's 实付金额property 
     */
    public java.math.BigDecimal getActAmount()
    {
        return getBigDecimal("actAmount");
    }
    public void setActAmount(java.math.BigDecimal item)
    {
        setBigDecimal("actAmount", item);
    }
    /**
     * Object:租赁配套资源付款明细分录's 实付日期property 
     */
    public java.util.Date getActPayDate()
    {
        return getDate("actPayDate");
    }
    public void setActPayDate(java.util.Date item)
    {
        setDate("actPayDate", item);
    }
    /**
     * Object:租赁配套资源付款明细分录's 付款明细序号property 
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
     * Object:租赁配套资源付款明细分录's 免租天数property 
     */
    public int getFreeDays()
    {
        return getInt("freeDays");
    }
    public void setFreeDays(int item)
    {
        setInt("freeDays", item);
    }
    /**
     * Object:租赁配套资源付款明细分录's 应付日期property 
     */
    public java.util.Date getAppPayDate()
    {
        return getDate("appPayDate");
    }
    public void setAppPayDate(java.util.Date item)
    {
        setDate("appPayDate", item);
    }
    /**
     * Object:租赁配套资源付款明细分录's 退款金额property 
     */
    public java.math.BigDecimal getRefundmentAmount()
    {
        return getBigDecimal("refundmentAmount");
    }
    public void setRefundmentAmount(java.math.BigDecimal item)
    {
        setBigDecimal("refundmentAmount", item);
    }
    /**
     * Object:租赁配套资源付款明细分录's 可退金额property 
     */
    public java.math.BigDecimal getCanRefundmentAmount()
    {
        return getBigDecimal("canRefundmentAmount");
    }
    public void setCanRefundmentAmount(java.math.BigDecimal item)
    {
        setBigDecimal("canRefundmentAmount", item);
    }
    /**
     * Object:租赁配套资源付款明细分录's 减免金额property 
     */
    public java.math.BigDecimal getRemissionAmount()
    {
        return getBigDecimal("remissionAmount");
    }
    public void setRemissionAmount(java.math.BigDecimal item)
    {
        setBigDecimal("remissionAmount", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("FF2704B3");
    }
}