package com.kingdee.eas.fdc.market;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractChannelTypeTreeInfo extends com.kingdee.eas.framework.TreeBaseInfo implements Serializable 
{
    public AbstractChannelTypeTreeInfo()
    {
        this("id");
    }
    protected AbstractChannelTypeTreeInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 渠道分类组别 's 单据头 property 
     */
    public com.kingdee.eas.fdc.market.ChannelTypeTreeInfo getParent()
    {
        return (com.kingdee.eas.fdc.market.ChannelTypeTreeInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.market.ChannelTypeTreeInfo item)
    {
        put("parent", item);
    }
    /**
     * Object: 渠道分类组别 's 营销项目 property 
     */
    public com.kingdee.eas.fdc.basedata.CostAccountInfo getCsotaccount()
    {
        return (com.kingdee.eas.fdc.basedata.CostAccountInfo)get("Csotaccount");
    }
    public void setCsotaccount(com.kingdee.eas.fdc.basedata.CostAccountInfo item)
    {
        put("Csotaccount", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("B1A4962A");
    }
}