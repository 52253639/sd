package com.kingdee.eas.fdc.finance;

import java.io.Serializable;

public class PayRequestSplitEntryInfo extends AbstractPayRequestSplitEntryInfo implements Serializable 
{
    public PayRequestSplitEntryInfo()
    {
        super();
    }
    protected PayRequestSplitEntryInfo(String pkField)
    {
        super(pkField);
    }
}