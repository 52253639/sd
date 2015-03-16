package com.kingdee.eas.fdc.tenancy;

import java.io.Serializable;

public class TenancyRoomChargeEntryInfo extends AbstractTenancyRoomChargeEntryInfo implements Serializable 
{
    public TenancyRoomChargeEntryInfo()
    {
        super();
    }
    protected TenancyRoomChargeEntryInfo(String pkField)
    {
        super(pkField);
    }
}