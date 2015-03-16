package com.kingdee.eas.fdc.tenancy;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractMarketingUnitSellProjectInfo extends com.kingdee.eas.framework.DataBaseInfo implements Serializable 
{
    public AbstractMarketingUnitSellProjectInfo()
    {
        this("id");
    }
    protected AbstractMarketingUnitSellProjectInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 营销单元管理项目 's 营销单元头 property 
     */
    public com.kingdee.eas.fdc.tenancy.MarketingUnitInfo getHead()
    {
        return (com.kingdee.eas.fdc.tenancy.MarketingUnitInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.tenancy.MarketingUnitInfo item)
    {
        put("head", item);
    }
    /**
     * Object: 营销单元管理项目 's 项目 property 
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
        return new BOSObjectType("B5FA2AE6");
    }
}