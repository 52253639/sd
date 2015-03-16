package com.kingdee.eas.fdc.market;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractPlanEntryE3Info extends com.kingdee.eas.framework.BillEntryBaseInfo implements Serializable 
{
    public AbstractPlanEntryE3Info()
    {
        this("id");
    }
    protected AbstractPlanEntryE3Info(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 计划效果 's null property 
     */
    public com.kingdee.eas.fdc.market.PlanEntryInfo getParent1()
    {
        return (com.kingdee.eas.fdc.market.PlanEntryInfo)get("parent1");
    }
    public void setParent1(com.kingdee.eas.fdc.market.PlanEntryInfo item)
    {
        put("parent1", item);
    }
    /**
     * Object:计划效果's 名称property 
     */
    public String getPlanName()
    {
        return getString("planName");
    }
    public void setPlanName(String item)
    {
        setString("planName", item);
    }
    /**
     * Object:计划效果's 计划数值property 
     */
    public String getColValue()
    {
        return getString("colValue");
    }
    public void setColValue(String item)
    {
        setString("colValue", item);
    }
    /**
     * Object:计划效果's 活动期间数值property 
     */
    public String getRemark()
    {
        return getString("remark");
    }
    public void setRemark(String item)
    {
        setString("remark", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("7F4A16A6");
    }
}