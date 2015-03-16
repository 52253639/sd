package com.kingdee.eas.fdc.tenancy;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractAttachRentAdjustInfo extends com.kingdee.eas.fdc.tenancy.TenBillBaseInfo implements Serializable 
{
    public AbstractAttachRentAdjustInfo()
    {
        this("id");
    }
    protected AbstractAttachRentAdjustInfo(String pkField)
    {
        super(pkField);
        put("entrys", new com.kingdee.eas.fdc.tenancy.AttachRentAdjustEntrysCollection());
    }
    /**
     * Object: 配套资源调租单 's 调租分录 property 
     */
    public com.kingdee.eas.fdc.tenancy.AttachRentAdjustEntrysCollection getEntrys()
    {
        return (com.kingdee.eas.fdc.tenancy.AttachRentAdjustEntrysCollection)get("entrys");
    }
    /**
     * Object:配套资源调租单's 是否执行property 
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
     * Object:配套资源调租单's 执行时间property 
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
     * Object: 配套资源调租单 's 项目 property 
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
        return new BOSObjectType("3B7A27F6");
    }
}