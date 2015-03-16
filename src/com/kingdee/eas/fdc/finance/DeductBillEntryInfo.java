package com.kingdee.eas.fdc.finance;

import java.io.Serializable;

public class DeductBillEntryInfo extends AbstractDeductBillEntryInfo implements Serializable 
{
    public DeductBillEntryInfo()
    {
        super();
    }
    protected DeductBillEntryInfo(String pkField)
    {
        super(pkField);
    }
}