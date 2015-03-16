package com.kingdee.eas.fdc.market;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractMarketManageChargeInfo extends com.kingdee.eas.framework.BillEntryBaseInfo implements Serializable 
{
    public AbstractMarketManageChargeInfo()
    {
        this("id");
    }
    protected AbstractMarketManageChargeInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 计划详细步骤 's null property 
     */
    public com.kingdee.eas.fdc.market.MarketManageInfo getParent()
    {
        return (com.kingdee.eas.fdc.market.MarketManageInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.market.MarketManageInfo item)
    {
        put("parent", item);
    }
    /**
     * Object: 计划详细步骤 's 负责人 property 
     */
    public com.kingdee.eas.basedata.person.PersonInfo getCustomer()
    {
        return (com.kingdee.eas.basedata.person.PersonInfo)get("customer");
    }
    public void setCustomer(com.kingdee.eas.basedata.person.PersonInfo item)
    {
        put("customer", item);
    }
    /**
     * Object:计划详细步骤's 参加人员property 
     */
    public String getPerson()
    {
        return getString("person");
    }
    public void setPerson(String item)
    {
        setString("person", item);
    }
    /**
     * Object:计划详细步骤's 开始时间property 
     */
    public java.util.Date getBeginDate()
    {
        return getDate("beginDate");
    }
    public void setBeginDate(java.util.Date item)
    {
        setDate("beginDate", item);
    }
    /**
     * Object:计划详细步骤's 结束时间property 
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
    /**
     * Object:计划详细步骤's 完成情况property 
     */
    public String getCompletion()
    {
        return getString("completion");
    }
    public void setCompletion(String item)
    {
        setString("completion", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("641DAE66");
    }
}