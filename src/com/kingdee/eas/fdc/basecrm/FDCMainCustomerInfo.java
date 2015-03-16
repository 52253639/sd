package com.kingdee.eas.fdc.basecrm;

import java.io.Serializable;

public class FDCMainCustomerInfo extends AbstractFDCMainCustomerInfo implements Serializable 
{
    public FDCMainCustomerInfo()
    {
        super();
    }
    protected FDCMainCustomerInfo(String pkField)
    {
        super(pkField);
    }
}