package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;

public class ChangeRoomInfo extends AbstractChangeRoomInfo implements Serializable 
{
    public ChangeRoomInfo()
    {
        super();
    }
    protected ChangeRoomInfo(String pkField)
    {
        super(pkField);
    }
}