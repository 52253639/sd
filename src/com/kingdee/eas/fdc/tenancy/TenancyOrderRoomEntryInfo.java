package com.kingdee.eas.fdc.tenancy;

import java.io.Serializable;

public class TenancyOrderRoomEntryInfo extends AbstractTenancyOrderRoomEntryInfo implements Serializable 
{
    public TenancyOrderRoomEntryInfo()
    {
        super();
    }
    protected TenancyOrderRoomEntryInfo(String pkField)
    {
        super(pkField);
    }
}