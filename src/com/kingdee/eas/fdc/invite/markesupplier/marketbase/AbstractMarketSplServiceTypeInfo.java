package com.kingdee.eas.fdc.invite.markesupplier.marketbase;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractMarketSplServiceTypeInfo extends com.kingdee.eas.fdc.basedata.FDCTreeBaseDataInfo implements Serializable 
{
    public AbstractMarketSplServiceTypeInfo()
    {
        this("id");
    }
    protected AbstractMarketSplServiceTypeInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 服务类型 's 父节点 property 
     */
    public com.kingdee.eas.fdc.invite.markesupplier.marketbase.MarketSplServiceTypeInfo getParent()
    {
        return (com.kingdee.eas.fdc.invite.markesupplier.marketbase.MarketSplServiceTypeInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.invite.markesupplier.marketbase.MarketSplServiceTypeInfo item)
    {
        put("parent", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("53AE7DE9");
    }
}