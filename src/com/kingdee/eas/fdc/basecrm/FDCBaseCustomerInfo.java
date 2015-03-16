package com.kingdee.eas.fdc.basecrm;

import java.io.Serializable;

public class FDCBaseCustomerInfo extends AbstractFDCBaseCustomerInfo implements Serializable 
{
    public FDCBaseCustomerInfo()
    {
        super();
    }
    protected FDCBaseCustomerInfo(String pkField)
    {
        super(pkField);
    }
}