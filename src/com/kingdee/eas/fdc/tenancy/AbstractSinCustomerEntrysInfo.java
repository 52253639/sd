package com.kingdee.eas.fdc.tenancy;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractSinCustomerEntrysInfo extends com.kingdee.eas.framework.CoreBillBaseInfo implements Serializable 
{
    public AbstractSinCustomerEntrysInfo()
    {
        this("id");
    }
    protected AbstractSinCustomerEntrysInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 诚意预留客户分录 's 诚意预留单头 property 
     */
    public com.kingdee.eas.fdc.tenancy.SincerObligateInfo getHead()
    {
        return (com.kingdee.eas.fdc.tenancy.SincerObligateInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.tenancy.SincerObligateInfo item)
    {
        put("head", item);
    }
    /**
     * Object: 诚意预留客户分录 's 房地产客户 property 
     */
    public com.kingdee.eas.fdc.sellhouse.FDCCustomerInfo getFdcCustomer()
    {
        return (com.kingdee.eas.fdc.sellhouse.FDCCustomerInfo)get("fdcCustomer");
    }
    public void setFdcCustomer(com.kingdee.eas.fdc.sellhouse.FDCCustomerInfo item)
    {
        put("fdcCustomer", item);
    }
    /**
     * Object:诚意预留客户分录's 出租比例property 
     */
    public java.math.BigDecimal getPropertyPercent()
    {
        return getBigDecimal("propertyPercent");
    }
    public void setPropertyPercent(java.math.BigDecimal item)
    {
        setBigDecimal("propertyPercent", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("4759782E");
    }
}