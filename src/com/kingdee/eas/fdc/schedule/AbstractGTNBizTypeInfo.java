package com.kingdee.eas.fdc.schedule;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractGTNBizTypeInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractGTNBizTypeInfo()
    {
        this("id");
    }
    protected AbstractGTNBizTypeInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 业务类型 's 业务类型 property 
     */
    public com.kingdee.eas.fdc.schedule.ScheduleBizTypeInfo getBizType()
    {
        return (com.kingdee.eas.fdc.schedule.ScheduleBizTypeInfo)get("bizType");
    }
    public void setBizType(com.kingdee.eas.fdc.schedule.ScheduleBizTypeInfo item)
    {
        put("bizType", item);
    }
    /**
     * Object: 业务类型 's 所属节点 property 
     */
    public com.kingdee.eas.fdc.schedule.GlobalTaskNodeInfo getGlobalTaskNode()
    {
        return (com.kingdee.eas.fdc.schedule.GlobalTaskNodeInfo)get("globalTaskNode");
    }
    public void setGlobalTaskNode(com.kingdee.eas.fdc.schedule.GlobalTaskNodeInfo item)
    {
        put("globalTaskNode", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("EB4FCAE2");
    }
}