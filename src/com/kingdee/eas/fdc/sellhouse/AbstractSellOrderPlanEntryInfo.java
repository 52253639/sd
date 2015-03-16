package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractSellOrderPlanEntryInfo extends com.kingdee.eas.framework.DataBaseInfo implements Serializable 
{
    public AbstractSellOrderPlanEntryInfo()
    {
        this("id");
    }
    protected AbstractSellOrderPlanEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 计划收款分录 's 盘次头 property 
     */
    public com.kingdee.eas.fdc.sellhouse.SellOrderInfo getHead()
    {
        return (com.kingdee.eas.fdc.sellhouse.SellOrderInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.sellhouse.SellOrderInfo item)
    {
        put("head", item);
    }
    /**
     * Object:计划收款分录's 比例property 
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
     * Object:计划收款分录's 计划日期property 
     */
    public java.util.Date getPlanDate()
    {
        return getDate("planDate");
    }
    public void setPlanDate(java.util.Date item)
    {
        setDate("planDate", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("FBA9F928");
    }
}