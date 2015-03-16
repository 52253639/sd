package com.kingdee.eas.fdc.tenancy;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractAttachResourceRentBillInfo extends com.kingdee.eas.fdc.tenancy.TenBillBaseInfo implements Serializable 
{
    public AbstractAttachResourceRentBillInfo()
    {
        this("id");
    }
    protected AbstractAttachResourceRentBillInfo(String pkField)
    {
        super(pkField);
        put("entrys", new com.kingdee.eas.fdc.tenancy.AttachResourceRentEntrysCollection());
    }
    /**
     * Object: 配套资源定租单 's 配套资源定租分录 property 
     */
    public com.kingdee.eas.fdc.tenancy.AttachResourceRentEntrysCollection getEntrys()
    {
        return (com.kingdee.eas.fdc.tenancy.AttachResourceRentEntrysCollection)get("entrys");
    }
    /**
     * Object:配套资源定租单's 是否执行property 
     */
    public boolean isIsExecuted()
    {
        return getBoolean("isExecuted");
    }
    public void setIsExecuted(boolean item)
    {
        setBoolean("isExecuted", item);
    }
    /**
     * Object:配套资源定租单's 是否失效property 
     */
    public boolean isIsInvalid()
    {
        return getBoolean("isInvalid");
    }
    public void setIsInvalid(boolean item)
    {
        setBoolean("isInvalid", item);
    }
    /**
     * Object:配套资源定租单's 执行时间property 
     */
    public java.sql.Timestamp getExecutedTime()
    {
        return getTimestamp("executedTime");
    }
    public void setExecutedTime(java.sql.Timestamp item)
    {
        setTimestamp("executedTime", item);
    }
    /**
     * Object: 配套资源定租单 's 项目 property 
     */
    public com.kingdee.eas.fdc.sellhouse.SellProjectInfo getSellProject()
    {
        return (com.kingdee.eas.fdc.sellhouse.SellProjectInfo)get("sellProject");
    }
    public void setSellProject(com.kingdee.eas.fdc.sellhouse.SellProjectInfo item)
    {
        put("sellProject", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("E8050E5C");
    }
}