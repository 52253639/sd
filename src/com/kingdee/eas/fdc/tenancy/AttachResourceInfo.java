package com.kingdee.eas.fdc.tenancy;

import java.io.Serializable;

public class AttachResourceInfo extends AbstractAttachResourceInfo implements Serializable 
{
    public AttachResourceInfo()
    {
        super();
    }
    protected AttachResourceInfo(String pkField)
    {
        super(pkField);
    }
}