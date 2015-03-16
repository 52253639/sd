package com.kingdee.eas.fdc.market;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractValuePlanInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractValuePlanInfo()
    {
        this("id");
    }
    protected AbstractValuePlanInfo(String pkField)
    {
        super(pkField);
        put("entry", new com.kingdee.eas.fdc.market.ValuePlanEntryCollection());
    }
    /**
     * Object: 销售计划 's 项目 property 
     */
    public com.kingdee.eas.fdc.basedata.ProjectBaseInfo getProject()
    {
        return (com.kingdee.eas.fdc.basedata.ProjectBaseInfo)get("project");
    }
    public void setProject(com.kingdee.eas.fdc.basedata.ProjectBaseInfo item)
    {
        put("project", item);
    }
    /**
     * Object: 销售计划 's 分录 property 
     */
    public com.kingdee.eas.fdc.market.ValuePlanEntryCollection getEntry()
    {
        return (com.kingdee.eas.fdc.market.ValuePlanEntryCollection)get("entry");
    }
    /**
     * Object:销售计划's 计划分解年property 
     */
    public int getPlanYear()
    {
        return getInt("planYear");
    }
    public void setPlanYear(int item)
    {
        setInt("planYear", item);
    }
    /**
     * Object:销售计划's 计划编制年份property 
     */
    public int getYear()
    {
        return getInt("year");
    }
    public void setYear(int item)
    {
        setInt("year", item);
    }
    /**
     * Object:销售计划's 计划编制月份property 
     */
    public int getMonth()
    {
        return getInt("month");
    }
    public void setMonth(int item)
    {
        setInt("month", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("C4A00589");
    }
}