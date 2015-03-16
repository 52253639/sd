package com.kingdee.eas.fdc.market;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractMarketManageMediaInfo extends com.kingdee.eas.framework.BillEntryBaseInfo implements Serializable 
{
    public AbstractMarketManageMediaInfo()
    {
        this("id");
    }
    protected AbstractMarketManageMediaInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 媒体管理 's null property 
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
     * Object: 媒体管理 's 媒体类型 property 
     */
    public com.kingdee.eas.fdc.market.MediaInfo getMediaType()
    {
        return (com.kingdee.eas.fdc.market.MediaInfo)get("mediaType");
    }
    public void setMediaType(com.kingdee.eas.fdc.market.MediaInfo item)
    {
        put("mediaType", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("A8EFA5F2");
    }
}