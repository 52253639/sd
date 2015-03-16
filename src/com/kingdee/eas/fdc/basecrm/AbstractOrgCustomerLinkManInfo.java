package com.kingdee.eas.fdc.basecrm;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractOrgCustomerLinkManInfo extends com.kingdee.eas.fdc.basecrm.FDCBaseLinkManInfo implements Serializable 
{
    public AbstractOrgCustomerLinkManInfo()
    {
        this("id");
    }
    protected AbstractOrgCustomerLinkManInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 组织客户联系人 's 组织客户 property 
     */
    public com.kingdee.eas.fdc.basecrm.FDCOrgCustomerInfo getOrgCustomer()
    {
        return (com.kingdee.eas.fdc.basecrm.FDCOrgCustomerInfo)get("orgCustomer");
    }
    public void setOrgCustomer(com.kingdee.eas.fdc.basecrm.FDCOrgCustomerInfo item)
    {
        put("orgCustomer", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("6855C61A");
    }
}