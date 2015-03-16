package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;

import com.kingdee.eas.fdc.basecrm.IPartCustomerInfo;

public class SHECustomerInfo extends AbstractSHECustomerInfo implements Serializable,IPartCustomerInfo 
{
    public SHECustomerInfo()
    {
        super();
    }
    protected SHECustomerInfo(String pkField)
    {
        super(pkField);
    }
}