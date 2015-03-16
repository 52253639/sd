package com.kingdee.eas.fdc.invite;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractNewQuotingPriceInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractNewQuotingPriceInfo()
    {
        this("id");
    }
    protected AbstractNewQuotingPriceInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 报价信息 's 招标清单文件 property 
     */
    public com.kingdee.eas.fdc.invite.NewListingInfo getListing()
    {
        return (com.kingdee.eas.fdc.invite.NewListingInfo)get("listing");
    }
    public void setListing(com.kingdee.eas.fdc.invite.NewListingInfo item)
    {
        put("listing", item);
    }
    /**
     * Object: 报价信息 's 投标人 property 
     */
    public com.kingdee.eas.basedata.master.cssp.SupplierInfo getSupplier()
    {
        return (com.kingdee.eas.basedata.master.cssp.SupplierInfo)get("supplier");
    }
    public void setSupplier(com.kingdee.eas.basedata.master.cssp.SupplierInfo item)
    {
        put("supplier", item);
    }
    /**
     * Object:报价信息's 投标报价property 
     */
    public java.math.BigDecimal getTotoalPrice()
    {
        return getBigDecimal("totoalPrice");
    }
    public void setTotoalPrice(java.math.BigDecimal item)
    {
        setBigDecimal("totoalPrice", item);
    }
    /**
     * Object:报价信息's 报价状态property 
     */
    public com.kingdee.eas.fdc.invite.QuotingPriceStatusEnum getStatus()
    {
        return com.kingdee.eas.fdc.invite.QuotingPriceStatusEnum.getEnum(getString("status"));
    }
    public void setStatus(com.kingdee.eas.fdc.invite.QuotingPriceStatusEnum item)
    {
		if (item != null) {
        setString("status", item.getValue());
		}
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("268DAEAC");
    }
}