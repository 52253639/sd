package com.kingdee.eas.fdc.tenancy;

import java.io.Serializable;

public class BrokerInfo extends AbstractBrokerInfo implements Serializable 
{
    public BrokerInfo()
    {
        super();
    }
    protected BrokerInfo(String pkField)
    {
        super(pkField);
    }
}