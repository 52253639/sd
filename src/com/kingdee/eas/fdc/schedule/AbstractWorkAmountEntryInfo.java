package com.kingdee.eas.fdc.schedule;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractWorkAmountEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractWorkAmountEntryInfo()
    {
        this("id");
    }
    protected AbstractWorkAmountEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 工程量录入单分录 's 所属单据 property 
     */
    public com.kingdee.eas.fdc.schedule.WorkAmountBillInfo getParent()
    {
        return (com.kingdee.eas.fdc.schedule.WorkAmountBillInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.schedule.WorkAmountBillInfo item)
    {
        put("parent", item);
    }
    /**
     * Object: 工程量录入单分录 's 任务 property 
     */
    public com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo getTask()
    {
        return (com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo)get("task");
    }
    public void setTask(com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo item)
    {
        put("task", item);
    }
    /**
     * Object:工程量录入单分录's 累计完工工程量property 
     */
    public java.math.BigDecimal getTotalAmount()
    {
        return getBigDecimal("totalAmount");
    }
    public void setTotalAmount(java.math.BigDecimal item)
    {
        setBigDecimal("totalAmount", item);
    }
    /**
     * Object:工程量录入单分录's 累计完工百分比property 
     */
    public java.math.BigDecimal getTotalPercent()
    {
        return getBigDecimal("totalPercent");
    }
    public void setTotalPercent(java.math.BigDecimal item)
    {
        setBigDecimal("totalPercent", item);
    }
    /**
     * Object:工程量录入单分录's 本次完工工程量property 
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
     * Object:工程量录入单分录's 本次完工百分比property 
     */
    public java.math.BigDecimal getPercent()
    {
        return getBigDecimal("percent");
    }
    public void setPercent(java.math.BigDecimal item)
    {
        setBigDecimal("percent", item);
    }
    /**
     * Object:工程量录入单分录's 完工日期property 
     */
    public java.util.Date getCompleteDate()
    {
        return getDate("completeDate");
    }
    public void setCompleteDate(java.util.Date item)
    {
        setDate("completeDate", item);
    }
    /**
     * Object:工程量录入单分录's 本次确认工程量property 
     */
    public java.math.BigDecimal getConfirmAmount()
    {
        return getBigDecimal("confirmAmount");
    }
    public void setConfirmAmount(java.math.BigDecimal item)
    {
        setBigDecimal("confirmAmount", item);
    }
    /**
     * Object:工程量录入单分录's 本次确认百分比property 
     */
    public java.math.BigDecimal getConfirmPercent()
    {
        return getBigDecimal("confirmPercent");
    }
    public void setConfirmPercent(java.math.BigDecimal item)
    {
        setBigDecimal("confirmPercent", item);
    }
    /**
     * Object:工程量录入单分录's 完成情况说明property 
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
     * Object: 工程量录入单分录 's null property 
     */
    public com.kingdee.eas.fdc.schedule.FDCWBSInfo getWbs()
    {
        return (com.kingdee.eas.fdc.schedule.FDCWBSInfo)get("wbs");
    }
    public void setWbs(com.kingdee.eas.fdc.schedule.FDCWBSInfo item)
    {
        put("wbs", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("44A9A893");
    }
}