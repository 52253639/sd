package com.kingdee.eas.fdc.basecrm;

import java.io.Serializable;

public class FDCOrgCustomerInfo extends AbstractFDCOrgCustomerInfo implements Serializable,IPartCustomerInfo 
{
    public FDCOrgCustomerInfo()
    {
        super();
    }
    protected FDCOrgCustomerInfo(String pkField)
    {
        super(pkField);
    }
}