package com.kingdee.eas.fdc.tenancy;

import java.io.Serializable;

public class RestReceivableInfo extends AbstractRestReceivableInfo implements Serializable 
{
    public RestReceivableInfo()
    {
        super();
    }
    protected RestReceivableInfo(String pkField)
    {
        super(pkField);
    }
}