package com.kingdee.eas.fdc.market;

import java.io.Serializable;

public class ProReferenceInfo extends AbstractProReferenceInfo implements Serializable 
{
    public ProReferenceInfo()
    {
        super();
    }
    protected ProReferenceInfo(String pkField)
    {
        super(pkField);
    }
}