package com.kingdee.eas.fdc.contract;

import java.io.Serializable;

public class SupplierApplyInfo extends AbstractSupplierApplyInfo implements Serializable 
{
    public SupplierApplyInfo()
    {
        super();
    }
    protected SupplierApplyInfo(String pkField)
    {
        super(pkField);
    }
}