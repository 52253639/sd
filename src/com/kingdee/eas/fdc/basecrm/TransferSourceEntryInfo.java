package com.kingdee.eas.fdc.basecrm;

import java.io.Serializable;

public class TransferSourceEntryInfo extends AbstractTransferSourceEntryInfo implements Serializable 
{
    public TransferSourceEntryInfo()
    {
        super();
    }
    protected TransferSourceEntryInfo(String pkField)
    {
        super(pkField);
    }
}