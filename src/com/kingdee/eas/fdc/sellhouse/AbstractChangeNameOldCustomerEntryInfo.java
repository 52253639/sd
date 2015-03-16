package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractChangeNameOldCustomerEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractChangeNameOldCustomerEntryInfo()
    {
        this("id");
    }
    protected AbstractChangeNameOldCustomerEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 更名新增旧客户分录 's 更名分录头 property 
     */
    public com.kingdee.eas.fdc.sellhouse.ChangeRecordEntryTwoInfo getParent()
    {
        return (com.kingdee.eas.fdc.sellhouse.ChangeRecordEntryTwoInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.sellhouse.ChangeRecordEntryTwoInfo item)
    {
        put("parent", item);
    }
    /**
     * Object: 更名新增旧客户分录 's 售楼客户 property 
     */
    public com.kingdee.eas.fdc.sellhouse.SHECustomerInfo getCustomer()
    {
        return (com.kingdee.eas.fdc.sellhouse.SHECustomerInfo)get("customer");
    }
    public void setCustomer(com.kingdee.eas.fdc.sellhouse.SHECustomerInfo item)
    {
        put("customer", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("0C3BDFE3");
    }
}