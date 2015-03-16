package com.kingdee.eas.fdc.tenancy;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractRentRemissionEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractRentRemissionEntryInfo()
    {
        this("id");
    }
    protected AbstractRentRemissionEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 租金减免分录 's 减免租金房间 property 
     */
    public com.kingdee.eas.fdc.tenancy.TenancyRoomPayListEntryInfo getRentRemissionRoom()
    {
        return (com.kingdee.eas.fdc.tenancy.TenancyRoomPayListEntryInfo)get("rentRemissionRoom");
    }
    public void setRentRemissionRoom(com.kingdee.eas.fdc.tenancy.TenancyRoomPayListEntryInfo item)
    {
        put("rentRemissionRoom", item);
    }
    /**
     * Object: 租金减免分录 's 款项类型 property 
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
     * Object:租金减免分录's 租期序号property 
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
     * Object:租金减免分录's 开始日期property 
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
     * Object:租金减免分录's 结束日期property 
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
     * Object:租金减免分录's 原应付金额property 
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
     * Object:租金减免分录's 减免金额property 
     */
    public java.math.BigDecimal getRemisionAmount()
    {
        return getBigDecimal("remisionAmount");
    }
    public void setRemisionAmount(java.math.BigDecimal item)
    {
        setBigDecimal("remisionAmount", item);
    }
    /**
     * Object: 租金减免分录 's 减免租金单 property 
     */
    public com.kingdee.eas.fdc.tenancy.RentRemissionInfo getHead()
    {
        return (com.kingdee.eas.fdc.tenancy.RentRemissionInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.tenancy.RentRemissionInfo item)
    {
        put("head", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("09A090FB");
    }
}