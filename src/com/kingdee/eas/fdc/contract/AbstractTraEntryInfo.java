package com.kingdee.eas.fdc.contract;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractTraEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractTraEntryInfo()
    {
        this("id");
    }
    protected AbstractTraEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 差旅费报销类分录 's 头 property 
     */
    public com.kingdee.eas.fdc.contract.ContractWithoutTextInfo getHead()
    {
        return (com.kingdee.eas.fdc.contract.ContractWithoutTextInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.contract.ContractWithoutTextInfo item)
    {
        put("head", item);
    }
    /**
     * Object:差旅费报销类分录's 开始日期property 
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
     * Object:差旅费报销类分录's 结束日期property 
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
     * Object:差旅费报销类分录's 出差天数property 
     */
    public int getDays()
    {
        return getInt("days");
    }
    public void setDays(int item)
    {
        setInt("days", item);
    }
    /**
     * Object:差旅费报销类分录's 起点property 
     */
    public String getStartPlace()
    {
        return getString("startPlace");
    }
    public void setStartPlace(String item)
    {
        setString("startPlace", item);
    }
    /**
     * Object:差旅费报销类分录's 终点property 
     */
    public String getEndPlace()
    {
        return getString("endPlace");
    }
    public void setEndPlace(String item)
    {
        setString("endPlace", item);
    }
    /**
     * Object:差旅费报销类分录's 机票property 
     */
    public java.math.BigDecimal getAirFee()
    {
        return getBigDecimal("airFee");
    }
    public void setAirFee(java.math.BigDecimal item)
    {
        setBigDecimal("airFee", item);
    }
    /**
     * Object:差旅费报销类分录's 车船票property 
     */
    public java.math.BigDecimal getCarFee()
    {
        return getBigDecimal("carFee");
    }
    public void setCarFee(java.math.BigDecimal item)
    {
        setBigDecimal("carFee", item);
    }
    /**
     * Object:差旅费报销类分录's 市内交通property 
     */
    public java.math.BigDecimal getCityFee()
    {
        return getBigDecimal("cityFee");
    }
    public void setCityFee(java.math.BigDecimal item)
    {
        setBigDecimal("cityFee", item);
    }
    /**
     * Object:差旅费报销类分录's 其他property 
     */
    public java.math.BigDecimal getOtherFee()
    {
        return getBigDecimal("otherFee");
    }
    public void setOtherFee(java.math.BigDecimal item)
    {
        setBigDecimal("otherFee", item);
    }
    /**
     * Object:差旅费报销类分录's 人数property 
     */
    public int getPersons()
    {
        return getInt("persons");
    }
    public void setPersons(int item)
    {
        setInt("persons", item);
    }
    /**
     * Object:差旅费报销类分录's 天数property 
     */
    public int getLiveDays()
    {
        return getInt("liveDays");
    }
    public void setLiveDays(int item)
    {
        setInt("liveDays", item);
    }
    /**
     * Object:差旅费报销类分录's 金额property 
     */
    public java.math.BigDecimal getLiveFee()
    {
        return getBigDecimal("liveFee");
    }
    public void setLiveFee(java.math.BigDecimal item)
    {
        setBigDecimal("liveFee", item);
    }
    /**
     * Object:差旅费报销类分录's 差旅补贴property 
     */
    public java.math.BigDecimal getAllowance()
    {
        return getBigDecimal("allowance");
    }
    public void setAllowance(java.math.BigDecimal item)
    {
        setBigDecimal("allowance", item);
    }
    /**
     * Object:差旅费报销类分录's 其他property 
     */
    public java.math.BigDecimal getOther()
    {
        return getBigDecimal("other");
    }
    public void setOther(java.math.BigDecimal item)
    {
        setBigDecimal("other", item);
    }
    /**
     * Object:差旅费报销类分录's 合计property 
     */
    public java.math.BigDecimal getTotal()
    {
        return getBigDecimal("total");
    }
    public void setTotal(java.math.BigDecimal item)
    {
        setBigDecimal("total", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("BBC4FBCA");
    }
}