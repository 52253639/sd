package com.kingdee.eas.fdc.finance;

import java.io.Serializable;

public class FDCBaseVoucherEntryInfo extends AbstractFDCBaseVoucherEntryInfo implements Serializable 
{
    public FDCBaseVoucherEntryInfo()
    {
        super();
    }
    protected FDCBaseVoucherEntryInfo(String pkField)
    {
        super(pkField);
    }
}