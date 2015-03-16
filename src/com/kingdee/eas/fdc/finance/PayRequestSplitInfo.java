package com.kingdee.eas.fdc.finance;

import java.io.Serializable;

public class PayRequestSplitInfo extends AbstractPayRequestSplitInfo implements Serializable 
{
    public PayRequestSplitInfo()
    {
        super();
    }
    protected PayRequestSplitInfo(String pkField)
    {
        super(pkField);
    }
}