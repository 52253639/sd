package com.kingdee.eas.fdc.invite.markesupplier;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractMarketSupplierStockSupplierServiceTypeInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractMarketSupplierStockSupplierServiceTypeInfo()
    {
        this("id");
    }
    protected AbstractMarketSupplierStockSupplierServiceTypeInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:供应商服务类型's 状态property 
     */
    public String getState()
    {
        return getString("state");
    }
    public void setState(String item)
    {
        setString("state", item);
    }
    /**
     * Object: 供应商服务类型 's 供应商 property 
     */
    public com.kingdee.eas.fdc.invite.markesupplier.MarketSupplierStockInfo getSupplier()
    {
        return (com.kingdee.eas.fdc.invite.markesupplier.MarketSupplierStockInfo)get("supplier");
    }
    public void setSupplier(com.kingdee.eas.fdc.invite.markesupplier.MarketSupplierStockInfo item)
    {
        put("supplier", item);
    }
    /**
     * Object: 供应商服务类型 's 服务类型 property 
     */
    public com.kingdee.eas.fdc.invite.markesupplier.marketbase.MarketSplServiceTypeInfo getServiceType()
    {
        return (com.kingdee.eas.fdc.invite.markesupplier.marketbase.MarketSplServiceTypeInfo)get("serviceType");
    }
    public void setServiceType(com.kingdee.eas.fdc.invite.markesupplier.marketbase.MarketSplServiceTypeInfo item)
    {
        put("serviceType", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("E0A00883");
    }
}