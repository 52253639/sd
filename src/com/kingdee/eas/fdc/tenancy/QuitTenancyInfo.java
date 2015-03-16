package com.kingdee.eas.fdc.tenancy;

import java.io.Serializable;

public class QuitTenancyInfo extends AbstractQuitTenancyInfo implements Serializable 
{
    public QuitTenancyInfo()
    {
        super();
    }
    protected QuitTenancyInfo(String pkField)
    {
        super(pkField);
    }
}