package com.kingdee.eas.fdc.finance;

import java.io.Serializable;

public class DeductBillInfo extends AbstractDeductBillInfo implements Serializable 
{
    public DeductBillInfo()
    {
        super();
    }
    protected DeductBillInfo(String pkField)
    {
        super(pkField);
    }
}