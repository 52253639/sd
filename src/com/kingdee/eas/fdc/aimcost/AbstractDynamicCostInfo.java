package com.kingdee.eas.fdc.aimcost;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractDynamicCostInfo extends com.kingdee.eas.framework.BillBaseInfo implements Serializable 
{
    public AbstractDynamicCostInfo()
    {
        this("id");
    }
    protected AbstractDynamicCostInfo(String pkField)
    {
        super(pkField);
        put("adjustEntrys", new com.kingdee.eas.fdc.aimcost.AdjustRecordEntryCollection());
        put("intendingCostEntrys", new com.kingdee.eas.fdc.aimcost.IntendingCostEntryCollection());
    }
    /**
     * Object: 动态成本 's 科目 property 
     */
    public com.kingdee.eas.fdc.basedata.CostAccountInfo getAccount()
    {
        return (com.kingdee.eas.fdc.basedata.CostAccountInfo)get("account");
    }
    public void setAccount(com.kingdee.eas.fdc.basedata.CostAccountInfo item)
    {
        put("account", item);
    }
    /**
     * Object:动态成本's 调整总额property 
     */
    public java.math.BigDecimal getAdjustSumAmount()
    {
        return getBigDecimal("adjustSumAmount");
    }
    public void setAdjustSumAmount(java.math.BigDecimal item)
    {
        setBigDecimal("adjustSumAmount", item);
    }
    /**
     * Object:动态成本's 待发生成本总额property 
     */
    public java.math.BigDecimal getIntendingCostSumAmount()
    {
        return getBigDecimal("intendingCostSumAmount");
    }
    public void setIntendingCostSumAmount(java.math.BigDecimal item)
    {
        setBigDecimal("intendingCostSumAmount", item);
    }
    /**
     * Object: 动态成本 's 调整信息分录 property 
     */
    public com.kingdee.eas.fdc.aimcost.AdjustRecordEntryCollection getAdjustEntrys()
    {
        return (com.kingdee.eas.fdc.aimcost.AdjustRecordEntryCollection)get("adjustEntrys");
    }
    /**
     * Object: 动态成本 's 待发生成本分录 property 
     */
    public com.kingdee.eas.fdc.aimcost.IntendingCostEntryCollection getIntendingCostEntrys()
    {
        return (com.kingdee.eas.fdc.aimcost.IntendingCostEntryCollection)get("intendingCostEntrys");
    }
    /**
     * Object: 动态成本 's 期间(数据发生改变的期间) property 
     */
    public com.kingdee.eas.basedata.assistant.PeriodInfo getPeriod()
    {
        return (com.kingdee.eas.basedata.assistant.PeriodInfo)get("period");
    }
    public void setPeriod(com.kingdee.eas.basedata.assistant.PeriodInfo item)
    {
        put("period", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("F5C696F5");
    }
}