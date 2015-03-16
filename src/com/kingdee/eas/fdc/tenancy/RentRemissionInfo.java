package com.kingdee.eas.fdc.tenancy;

import java.io.Serializable;

public class RentRemissionInfo extends AbstractRentRemissionInfo implements Serializable 
{
    public RentRemissionInfo()
    {
        super();
    }
    protected RentRemissionInfo(String pkField)
    {
        super(pkField);
    }
}