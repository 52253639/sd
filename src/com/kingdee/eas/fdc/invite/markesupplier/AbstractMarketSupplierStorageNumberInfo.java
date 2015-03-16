package com.kingdee.eas.fdc.invite.markesupplier;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractMarketSupplierStorageNumberInfo extends com.kingdee.eas.fdc.invite.supplier.SupplierStorageNumberInfo implements Serializable 
{
    public AbstractMarketSupplierStorageNumberInfo()
    {
        this("id");
    }
    protected AbstractMarketSupplierStorageNumberInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:营销供应商入库编码's nullproperty 
     */
    public com.kingdee.bos.util.BOSUuid getId()
    {
        return getBOSUuid("id");
    }
    public void setId(com.kingdee.bos.util.BOSUuid item)
    {
        setBOSUuid("id", item);
    }
    /**
     * Object: 营销供应商入库编码 's 供应商档案登记 property 
     */
    public com.kingdee.eas.fdc.invite.markesupplier.MarketSupplierStockInfo getMarketSupplier()
    {
        return (com.kingdee.eas.fdc.invite.markesupplier.MarketSupplierStockInfo)get("MarketSupplier");
    }
    public void setMarketSupplier(com.kingdee.eas.fdc.invite.markesupplier.MarketSupplierStockInfo item)
    {
        put("MarketSupplier", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("77110E0E");
    }
}