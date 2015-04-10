package com.kingdee.eas.fdc.tenancy;

import java.io.Serializable;

public class RentFreeBillInfo extends AbstractRentFreeBillInfo implements Serializable 
{
    public RentFreeBillInfo()
    {
        super();
    }
    protected RentFreeBillInfo(String pkField)
    {
        super(pkField);
    }
}