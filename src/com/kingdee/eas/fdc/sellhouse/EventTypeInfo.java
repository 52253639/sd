package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;

public class EventTypeInfo extends AbstractEventTypeInfo implements Serializable 
{
    public EventTypeInfo()
    {
        super();
    }
    protected EventTypeInfo(String pkField)
    {
        super(pkField);
    }
}