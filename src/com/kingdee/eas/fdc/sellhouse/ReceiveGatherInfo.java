package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;

public class ReceiveGatherInfo extends AbstractReceiveGatherInfo implements Serializable 
{
    public ReceiveGatherInfo()
    {
        super();
    }
    protected ReceiveGatherInfo(String pkField)
    {
        super(pkField);
    }
}