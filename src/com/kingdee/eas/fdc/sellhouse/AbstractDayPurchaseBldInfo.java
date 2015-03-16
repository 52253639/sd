package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractDayPurchaseBldInfo extends com.kingdee.eas.framework.CoreBaseInfo implements Serializable 
{
    public AbstractDayPurchaseBldInfo()
    {
        this("id");
    }
    protected AbstractDayPurchaseBldInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 月结认购楼栋 's 主表 property 
     */
    public com.kingdee.eas.fdc.sellhouse.DayMainTableInfo getDayMain()
    {
        return (com.kingdee.eas.fdc.sellhouse.DayMainTableInfo)get("dayMain");
    }
    public void setDayMain(com.kingdee.eas.fdc.sellhouse.DayMainTableInfo item)
    {
        put("dayMain", item);
    }
    /**
     * Object: 月结认购楼栋 's 搂栋 property 
     */
    public com.kingdee.eas.fdc.sellhouse.BuildingInfo getBuilding()
    {
        return (com.kingdee.eas.fdc.sellhouse.BuildingInfo)get("building");
    }
    public void setBuilding(com.kingdee.eas.fdc.sellhouse.BuildingInfo item)
    {
        put("building", item);
    }
    /**
     * Object:月结认购楼栋's 已售合同数property 
     */
    public int getCount()
    {
        return getInt("count");
    }
    public void setCount(int item)
    {
        setInt("count", item);
    }
    /**
     * Object:月结认购楼栋's 已售合同金额property 
     */
    public java.math.BigDecimal getAmount()
    {
        return getBigDecimal("amount");
    }
    public void setAmount(java.math.BigDecimal item)
    {
        setBigDecimal("amount", item);
    }
    /**
     * Object:月结认购楼栋's 已售面积property 
     */
    public java.math.BigDecimal getArea()
    {
        return getBigDecimal("area");
    }
    public void setArea(java.math.BigDecimal item)
    {
        setBigDecimal("area", item);
    }
    /**
     * Object:月结认购楼栋's 已售面积差额property 
     */
    public java.math.BigDecimal getCosate()
    {
        return getBigDecimal("Cosate");
    }
    public void setCosate(java.math.BigDecimal item)
    {
        setBigDecimal("Cosate", item);
    }
    /**
     * Object:月结认购楼栋's 预收款回款property 
     */
    public java.math.BigDecimal getPreMoney()
    {
        return getBigDecimal("preMoney");
    }
    public void setPreMoney(java.math.BigDecimal item)
    {
        setBigDecimal("preMoney", item);
    }
    /**
     * Object:月结认购楼栋's 预定金回款property 
     */
    public java.math.BigDecimal getPreconcertMoney()
    {
        return getBigDecimal("preconcertMoney");
    }
    public void setPreconcertMoney(java.math.BigDecimal item)
    {
        setBigDecimal("preconcertMoney", item);
    }
    /**
     * Object:月结认购楼栋's 定金回款property 
     */
    public java.math.BigDecimal getEarnestMoney()
    {
        return getBigDecimal("earnestMoney");
    }
    public void setEarnestMoney(java.math.BigDecimal item)
    {
        setBigDecimal("earnestMoney", item);
    }
    /**
     * Object:月结认购楼栋's 首期回款property 
     */
    public java.math.BigDecimal getFisrtAmount()
    {
        return getBigDecimal("fisrtAmount");
    }
    public void setFisrtAmount(java.math.BigDecimal item)
    {
        setBigDecimal("fisrtAmount", item);
    }
    /**
     * Object:月结认购楼栋's 楼款回款property 
     */
    public java.math.BigDecimal getHouseAmount()
    {
        return getBigDecimal("houseAmount");
    }
    public void setHouseAmount(java.math.BigDecimal item)
    {
        setBigDecimal("houseAmount", item);
    }
    /**
     * Object:月结认购楼栋's 按揭款回款property 
     */
    public java.math.BigDecimal getLoanAmount()
    {
        return getBigDecimal("loanAmount");
    }
    public void setLoanAmount(java.math.BigDecimal item)
    {
        setBigDecimal("loanAmount", item);
    }
    /**
     * Object:月结认购楼栋's 公积金回款property 
     */
    public java.math.BigDecimal getAccFundAmount()
    {
        return getBigDecimal("accFundAmount");
    }
    public void setAccFundAmount(java.math.BigDecimal item)
    {
        setBigDecimal("accFundAmount", item);
    }
    /**
     * Object:月结认购楼栋's 补差款回款property 
     */
    public java.math.BigDecimal getCompensateAmount()
    {
        return getBigDecimal("compensateAmount");
    }
    public void setCompensateAmount(java.math.BigDecimal item)
    {
        setBigDecimal("compensateAmount", item);
    }
    /**
     * Object:月结认购楼栋's 手续费回款property 
     */
    public java.math.BigDecimal getCommissionCharge()
    {
        return getBigDecimal("commissionCharge");
    }
    public void setCommissionCharge(java.math.BigDecimal item)
    {
        setBigDecimal("commissionCharge", item);
    }
    /**
     * Object:月结认购楼栋's 滞纳金回款property 
     */
    public java.math.BigDecimal getLateFee()
    {
        return getBigDecimal("lateFee");
    }
    public void setLateFee(java.math.BigDecimal item)
    {
        setBigDecimal("lateFee", item);
    }
    /**
     * Object:月结认购楼栋's 退款回款property 
     */
    public java.math.BigDecimal getRefundment()
    {
        return getBigDecimal("refundment");
    }
    public void setRefundment(java.math.BigDecimal item)
    {
        setBigDecimal("refundment", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("17CE2AF8");
    }
}