package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;

public class RoomLoanInfo extends AbstractRoomLoanInfo implements Serializable 
{
    public RoomLoanInfo()
    {
        super();
    }
    protected RoomLoanInfo(String pkField)
    {
        super(pkField);
    }
}