package com.kingdee.eas.fdc.basecrm;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractSHERevCustEntryInfo extends com.kingdee.eas.framework.CoreBaseInfo implements Serializable 
{
    public AbstractSHERevCustEntryInfo()
    {
        this("id");
    }
    protected AbstractSHERevCustEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 客户分录 's 收款单 property 
     */
    public com.kingdee.eas.fdc.basecrm.SHERevBillInfo getHead()
    {
        return (com.kingdee.eas.fdc.basecrm.SHERevBillInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.basecrm.SHERevBillInfo item)
    {
        put("head", item);
    }
    /**
     * Object: 客户分录 's 客户 property 
     */
    public com.kingdee.eas.fdc.sellhouse.SHECustomerInfo getSheCustomer()
    {
        return (com.kingdee.eas.fdc.sellhouse.SHECustomerInfo)get("sheCustomer");
    }
    public void setSheCustomer(com.kingdee.eas.fdc.sellhouse.SHECustomerInfo item)
    {
        put("sheCustomer", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("8B9C0270");
    }
}