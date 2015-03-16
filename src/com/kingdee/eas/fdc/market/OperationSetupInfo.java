package com.kingdee.eas.fdc.market;

import java.io.Serializable;

public class OperationSetupInfo extends AbstractOperationSetupInfo implements Serializable 
{
    public OperationSetupInfo()
    {
        super();
    }
    protected OperationSetupInfo(String pkField)
    {
        super(pkField);
    }
}