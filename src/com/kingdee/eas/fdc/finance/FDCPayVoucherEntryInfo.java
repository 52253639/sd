package com.kingdee.eas.fdc.finance;

import java.io.Serializable;

public class FDCPayVoucherEntryInfo extends AbstractFDCPayVoucherEntryInfo implements Serializable 
{
    public FDCPayVoucherEntryInfo()
    {
        super();
    }
    protected FDCPayVoucherEntryInfo(String pkField)
    {
        super(pkField);
    }
}