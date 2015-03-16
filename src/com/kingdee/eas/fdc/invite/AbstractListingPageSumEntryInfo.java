package com.kingdee.eas.fdc.invite;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractListingPageSumEntryInfo extends com.kingdee.eas.framework.CoreBaseInfo implements Serializable 
{
    public AbstractListingPageSumEntryInfo()
    {
        this("id");
    }
    protected AbstractListingPageSumEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 清单页签汇总数 's 页签 property 
     */
    public com.kingdee.eas.fdc.invite.NewListingPageInfo getPage()
    {
        return (com.kingdee.eas.fdc.invite.NewListingPageInfo)get("page");
    }
    public void setPage(com.kingdee.eas.fdc.invite.NewListingPageInfo item)
    {
        put("page", item);
    }
    /**
     * Object:清单页签汇总数's 金额property 
     */
    public java.math.BigDecimal getAmount()
    {
        return getBigDecimal("amount");
    }
    public void setAmount(java.math.BigDecimal item)
    {
        setBigDecimal("amount", item);
    }
    /**
     * Object: 清单页签汇总数 's 报价人 property 
     */
    public com.kingdee.eas.fdc.invite.NewQuotingPriceInfo getQuotingPrice()
    {
        return (com.kingdee.eas.fdc.invite.NewQuotingPriceInfo)get("quotingPrice");
    }
    public void setQuotingPrice(com.kingdee.eas.fdc.invite.NewQuotingPriceInfo item)
    {
        put("quotingPrice", item);
    }
    /**
     * Object:清单页签汇总数's 报价类型property 
     */
    public com.kingdee.eas.fdc.invite.QuotingPriceTypeEnum getType()
    {
        return com.kingdee.eas.fdc.invite.QuotingPriceTypeEnum.getEnum(getString("type"));
    }
    public void setType(com.kingdee.eas.fdc.invite.QuotingPriceTypeEnum item)
    {
		if (item != null) {
        setString("type", item.getValue());
		}
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("5B9B8396");
    }
}