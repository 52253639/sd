package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;

public class RoomPriceAdjustEntryInfo extends AbstractRoomPriceAdjustEntryInfo implements Serializable 
{
    public RoomPriceAdjustEntryInfo()
    {
        super();
    }
    protected RoomPriceAdjustEntryInfo(String pkField)
    {
        super(pkField);
    }
    
    //标志是否改动过
    private boolean isModyfied = false;

	public boolean isModyfied() {
		return isModyfied;
	}
	public void setModyfied(boolean isModyfied) {
		this.isModyfied = isModyfied;
	}
}