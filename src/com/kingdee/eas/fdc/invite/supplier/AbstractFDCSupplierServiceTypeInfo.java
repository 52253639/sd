package com.kingdee.eas.fdc.invite.supplier;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractFDCSupplierServiceTypeInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractFDCSupplierServiceTypeInfo()
    {
        this("id");
    }
    protected AbstractFDCSupplierServiceTypeInfo(String pkField)
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
    public com.kingdee.eas.fdc.invite.supplier.SupplierStockInfo getSupplier()
    {
        return (com.kingdee.eas.fdc.invite.supplier.SupplierStockInfo)get("supplier");
    }
    public void setSupplier(com.kingdee.eas.fdc.invite.supplier.SupplierStockInfo item)
    {
        put("supplier", item);
    }
    /**
     * Object: 供应商服务类型 's 服务类型 property 
     */
    public com.kingdee.eas.fdc.invite.supplier.FDCSplServiceTypeInfo getServiceType()
    {
        return (com.kingdee.eas.fdc.invite.supplier.FDCSplServiceTypeInfo)get("serviceType");
    }
    public void setServiceType(com.kingdee.eas.fdc.invite.supplier.FDCSplServiceTypeInfo item)
    {
        put("serviceType", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("591DD2B6");
    }
}