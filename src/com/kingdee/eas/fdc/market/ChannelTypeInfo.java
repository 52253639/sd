package com.kingdee.eas.fdc.market;

import java.io.Serializable;

public class ChannelTypeInfo extends AbstractChannelTypeInfo implements Serializable 
{
    public ChannelTypeInfo()
    {
        super();
    }
    protected ChannelTypeInfo(String pkField)
    {
        super(pkField);
    }
}