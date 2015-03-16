package com.kingdee.eas.fdc.tenancy;

import java.io.Serializable;

public class SurrenderReasonInfo extends AbstractSurrenderReasonInfo implements Serializable 
{
    public SurrenderReasonInfo()
    {
        super();
    }
    protected SurrenderReasonInfo(String pkField)
    {
        super(pkField);
    }
}