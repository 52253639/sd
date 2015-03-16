package com.kingdee.eas.fdc.market;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractMovementPlanE4Info extends com.kingdee.eas.framework.BillEntryBaseInfo implements Serializable 
{
    public AbstractMovementPlanE4Info()
    {
        this("id");
    }
    protected AbstractMovementPlanE4Info(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 计划详细步骤 's null property 
     */
    public com.kingdee.eas.fdc.market.MovementPlanInfo getParent()
    {
        return (com.kingdee.eas.fdc.market.MovementPlanInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.market.MovementPlanInfo item)
    {
        put("parent", item);
    }
    /**
     * Object: 计划详细步骤 's 负责人 property 
     */
    public com.kingdee.eas.basedata.person.PersonInfo getResponsiblePers()
    {
        return (com.kingdee.eas.basedata.person.PersonInfo)get("responsiblePers");
    }
    public void setResponsiblePers(com.kingdee.eas.basedata.person.PersonInfo item)
    {
        put("responsiblePers", item);
    }
    /**
     * Object:计划详细步骤's 参加人员property 
     */
    public String getPersons()
    {
        return getString("persons");
    }
    public void setPersons(String item)
    {
        setString("persons", item);
    }
    /**
     * Object:计划详细步骤's 开始时间property 
     */
    public java.util.Date getStartTime()
    {
        return getDate("startTime");
    }
    public void setStartTime(java.util.Date item)
    {
        setDate("startTime", item);
    }
    /**
     * Object:计划详细步骤's 结束时间property 
     */
    public java.util.Date getEndTime()
    {
        return getDate("endTime");
    }
    public void setEndTime(java.util.Date item)
    {
        setDate("endTime", item);
    }
    /**
     * Object:计划详细步骤's 详细时间property 
     */
    public String getActiveTime()
    {
        return getString("activeTime");
    }
    public void setActiveTime(String item)
    {
        setString("activeTime", item);
    }
    /**
     * Object:计划详细步骤's 工作内容property 
     */
    public String getContent()
    {
        return getString("content");
    }
    public void setContent(String item)
    {
        setString("content", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("06163F18");
    }
}