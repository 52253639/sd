package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractSHECustomerLinkManInfo extends com.kingdee.eas.fdc.basecrm.FDCBaseLinkManInfo implements Serializable 
{
    public AbstractSHECustomerLinkManInfo()
    {
        this("id");
    }
    protected AbstractSHECustomerLinkManInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 售楼客户联系人 's 售楼客户 property 
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
        return new BOSObjectType("4ED0F48D");
    }
}