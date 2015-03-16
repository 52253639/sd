package com.kingdee.eas.fdc.contract.programming;

import java.io.Serializable;

public class PTEEnonomyInfo extends AbstractPTEEnonomyInfo implements Serializable 
{
    public PTEEnonomyInfo()
    {
        super();
    }
    protected PTEEnonomyInfo(String pkField)
    {
        super(pkField);
    }
}