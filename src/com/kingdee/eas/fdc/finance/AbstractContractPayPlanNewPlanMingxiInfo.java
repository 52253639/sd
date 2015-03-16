package com.kingdee.eas.fdc.finance;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractContractPayPlanNewPlanMingxiInfo extends com.kingdee.eas.framework.BillEntryBaseInfo implements Serializable 
{
    public AbstractContractPayPlanNewPlanMingxiInfo()
    {
        this("id");
    }
    protected AbstractContractPayPlanNewPlanMingxiInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 付款计划明细 's null property 
     */
    public com.kingdee.eas.fdc.finance.ContractPayPlanNewInfo getParent()
    {
        return (com.kingdee.eas.fdc.finance.ContractPayPlanNewInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.finance.ContractPayPlanNewInfo item)
    {
        put("parent", item);
    }
    /**
     * Object:付款计划明细's 付款事项property 
     */
    public String getShixiang()
    {
        return getString("shixiang");
    }
    public void setShixiang(String item)
    {
        setString("shixiang", item);
    }
    /**
     * Object: 付款计划明细 's 事项明细 property 
     */
    public com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo getShixiangmx()
    {
        return (com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo)get("shixiangmx");
    }
    public void setShixiangmx(com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo item)
    {
        put("shixiangmx", item);
    }
    /**
     * Object:付款计划明细's 计划开始日期property 
     */
    public java.util.Date getPlanStratDate()
    {
        return getDate("planStratDate");
    }
    public void setPlanStratDate(java.util.Date item)
    {
        setDate("planStratDate", item);
    }
    /**
     * Object:付款计划明细's 计划完工日期property 
     */
    public java.util.Date getPlanEndDate()
    {
        return getDate("planEndDate");
    }
    public void setPlanEndDate(java.util.Date item)
    {
        setDate("planEndDate", item);
    }
    /**
     * Object:付款计划明细's 计划付款日期property 
     */
    public java.util.Date getPlanDate()
    {
        return getDate("planDate");
    }
    public void setPlanDate(java.util.Date item)
    {
        setDate("planDate", item);
    }
    /**
     * Object:付款计划明细's 延迟支付天数property 
     */
    public java.math.BigDecimal getDayNumber()
    {
        return getBigDecimal("dayNumber");
    }
    public void setDayNumber(java.math.BigDecimal item)
    {
        setBigDecimal("dayNumber", item);
    }
    /**
     * Object:付款计划明细's 计划付款比例property 
     */
    public java.math.BigDecimal getFukuanBili()
    {
        return getBigDecimal("fukuanBili");
    }
    public void setFukuanBili(java.math.BigDecimal item)
    {
        setBigDecimal("fukuanBili", item);
    }
    /**
     * Object:付款计划明细's 计划应付金额property 
     */
    public java.math.BigDecimal getYingfuMoney()
    {
        return getBigDecimal("yingfuMoney");
    }
    public void setYingfuMoney(java.math.BigDecimal item)
    {
        setBigDecimal("yingfuMoney", item);
    }
    /**
     * Object:付款计划明细's 备注property 
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
     * Object:付款计划明细's 是否可以编辑property 
     */
    public char getIsEdit()
    {
        return ((Character)get("isEdit")).charValue();
    }
    public void setIsEdit(char item)
    {
        put("isEdit", new Character(item));
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("817A589F");
    }
}