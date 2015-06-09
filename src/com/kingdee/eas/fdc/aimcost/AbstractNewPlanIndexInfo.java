package com.kingdee.eas.fdc.aimcost;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractNewPlanIndexInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractNewPlanIndexInfo()
    {
        this("id");
    }
    protected AbstractNewPlanIndexInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 规划指标 's 头 property 
     */
    public com.kingdee.eas.fdc.aimcost.MeasureCostInfo getHead()
    {
        return (com.kingdee.eas.fdc.aimcost.MeasureCostInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.aimcost.MeasureCostInfo item)
    {
        put("head", item);
    }
    /**
     * Object: 规划指标 's 规划指标 property 
     */
    public com.kingdee.eas.fdc.aimcost.PlanIndexConfigInfo getConfig()
    {
        return (com.kingdee.eas.fdc.aimcost.PlanIndexConfigInfo)get("config");
    }
    public void setConfig(com.kingdee.eas.fdc.aimcost.PlanIndexConfigInfo item)
    {
        put("config", item);
    }
    /**
     * Object:规划指标's 值property 
     */
    public String getValue()
    {
        return getString("value");
    }
    public void setValue(String item)
    {
        setString("value", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("302E8840");
    }
}