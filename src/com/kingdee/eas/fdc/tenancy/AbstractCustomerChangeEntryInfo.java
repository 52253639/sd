package com.kingdee.eas.fdc.tenancy;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractCustomerChangeEntryInfo extends com.kingdee.eas.framework.CoreBaseInfo implements Serializable 
{
    public AbstractCustomerChangeEntryInfo()
    {
        this("id");
    }
    protected AbstractCustomerChangeEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:变更分录's 出租比例property 
     */
    public java.math.BigDecimal getPropertyPercent()
    {
        return getBigDecimal("propertyPercent");
    }
    public void setPropertyPercent(java.math.BigDecimal item)
    {
        setBigDecimal("propertyPercent", item);
    }
    /**
     * Object:变更分录's 描述property 
     */
    public String getDescription()
    {
        return getString("description");
    }
    public void setDescription(String item)
    {
        setString("description", item);
    }
    /**
     * Object: 变更分录 's 更名单据头 property 
     */
    public com.kingdee.eas.fdc.tenancy.CustomerChangeNameInfo getCustomerChangeName()
    {
        return (com.kingdee.eas.fdc.tenancy.CustomerChangeNameInfo)get("customerChangeName");
    }
    public void setCustomerChangeName(com.kingdee.eas.fdc.tenancy.CustomerChangeNameInfo item)
    {
        put("customerChangeName", item);
    }
    /**
     * Object: 变更分录 's 房地产客户 property 
     */
    public com.kingdee.eas.fdc.sellhouse.FDCCustomerInfo getFdcCustomer()
    {
        return (com.kingdee.eas.fdc.sellhouse.FDCCustomerInfo)get("fdcCustomer");
    }
    public void setFdcCustomer(com.kingdee.eas.fdc.sellhouse.FDCCustomerInfo item)
    {
        put("fdcCustomer", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("55D2379B");
    }
}