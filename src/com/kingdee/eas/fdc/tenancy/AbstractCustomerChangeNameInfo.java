package com.kingdee.eas.fdc.tenancy;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractCustomerChangeNameInfo extends com.kingdee.eas.fdc.tenancy.TenBillBaseInfo implements Serializable 
{
    public AbstractCustomerChangeNameInfo()
    {
        this("id");
    }
    protected AbstractCustomerChangeNameInfo(String pkField)
    {
        super(pkField);
        put("newCustomer", new com.kingdee.eas.fdc.tenancy.CustomerChangeEntryCollection());
        put("oldCustomer", new com.kingdee.eas.fdc.tenancy.CustomerChangeOldEntryCollection());
    }
    /**
     * Object:客户更名's 变更原因property 
     */
    public String getChangeReson()
    {
        return getString("changeReson");
    }
    public void setChangeReson(String item)
    {
        setString("changeReson", item);
    }
    /**
     * Object: 客户更名 's 租赁合同 property 
     */
    public com.kingdee.eas.fdc.tenancy.TenancyBillInfo getTenancyBill()
    {
        return (com.kingdee.eas.fdc.tenancy.TenancyBillInfo)get("tenancyBill");
    }
    public void setTenancyBill(com.kingdee.eas.fdc.tenancy.TenancyBillInfo item)
    {
        put("tenancyBill", item);
    }
    /**
     * Object: 客户更名 's 新客户 property 
     */
    public com.kingdee.eas.fdc.tenancy.CustomerChangeEntryCollection getNewCustomer()
    {
        return (com.kingdee.eas.fdc.tenancy.CustomerChangeEntryCollection)get("newCustomer");
    }
    /**
     * Object: 客户更名 's 旧客户 property 
     */
    public com.kingdee.eas.fdc.tenancy.CustomerChangeOldEntryCollection getOldCustomer()
    {
        return (com.kingdee.eas.fdc.tenancy.CustomerChangeOldEntryCollection)get("oldCustomer");
    }
    /**
     * Object: 客户更名 's 租售项目 property 
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
        return new BOSObjectType("555D4242");
    }
}