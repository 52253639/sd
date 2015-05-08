package com.kingdee.eas.fdc.schedule;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractTaskCostInfoInfo extends com.kingdee.eas.fdc.schedule.SchedulePropBaseExtInfo implements Serializable 
{
    public AbstractTaskCostInfoInfo()
    {
        this("id");
    }
    protected AbstractTaskCostInfoInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:任务成本信息's 目标成本property 
     */
    public java.math.BigDecimal getAimCost()
    {
        return getBigDecimal("aimCost");
    }
    public void setAimCost(java.math.BigDecimal item)
    {
        setBigDecimal("aimCost", item);
    }
    /**
     * Object:任务成本信息's 实际成本property 
     */
    public java.math.BigDecimal getActCost()
    {
        return getBigDecimal("actCost");
    }
    public void setActCost(java.math.BigDecimal item)
    {
        setBigDecimal("actCost", item);
    }
    /**
     * Object:任务成本信息's 成本偏差property 
     */
    public java.math.BigDecimal getCostDeviation()
    {
        return getBigDecimal("costDeviation");
    }
    public void setCostDeviation(java.math.BigDecimal item)
    {
        setBigDecimal("costDeviation", item);
    }
    /**
     * Object:任务成本信息's 成本备注property 
     */
    public String getCostMemo()
    {
        return getString("costMemo");
    }
    public void setCostMemo(String item)
    {
        setString("costMemo", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("DAFB3996");
    }
}