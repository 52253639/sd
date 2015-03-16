package com.kingdee.eas.fdc.invite;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractRefPriceInfo extends com.kingdee.eas.framework.CoreBaseInfo implements Serializable 
{
    public AbstractRefPriceInfo()
    {
        this("id");
    }
    protected AbstractRefPriceInfo(String pkField)
    {
        super(pkField);
        put("entrys", new com.kingdee.eas.fdc.invite.RefPriceEntryCollection());
    }
    /**
     * Object: 参考价 's 子目 property 
     */
    public com.kingdee.eas.fdc.invite.ListingItemInfo getItem()
    {
        return (com.kingdee.eas.fdc.invite.ListingItemInfo)get("item");
    }
    public void setItem(com.kingdee.eas.fdc.invite.ListingItemInfo item)
    {
        put("item", item);
    }
    /**
     * Object:参考价's 日期property 
     */
    public java.util.Date getDate()
    {
        return getDate("date");
    }
    public void setDate(java.util.Date item)
    {
        setDate("date", item);
    }
    /**
     * Object: 参考价 's 分录 property 
     */
    public com.kingdee.eas.fdc.invite.RefPriceEntryCollection getEntrys()
    {
        return (com.kingdee.eas.fdc.invite.RefPriceEntryCollection)get("entrys");
    }
    /**
     * Object: 参考价 's 报价信息 property 
     */
    public com.kingdee.eas.fdc.invite.NewQuotingPriceInfo getQuotingContent()
    {
        return (com.kingdee.eas.fdc.invite.NewQuotingPriceInfo)get("quotingContent");
    }
    public void setQuotingContent(com.kingdee.eas.fdc.invite.NewQuotingPriceInfo item)
    {
        put("quotingContent", item);
    }
    /**
     * Object: 参考价 's 清单 property 
     */
    public com.kingdee.eas.fdc.invite.NewListingInfo getListing()
    {
        return (com.kingdee.eas.fdc.invite.NewListingInfo)get("listing");
    }
    public void setListing(com.kingdee.eas.fdc.invite.NewListingInfo item)
    {
        put("listing", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("51008B9A");
    }
}