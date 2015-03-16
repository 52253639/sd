package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;

public class RoomPropertyBookInfo extends AbstractRoomPropertyBookInfo implements Serializable 
{
    public RoomPropertyBookInfo()
    {
        super();
    }
    protected RoomPropertyBookInfo(String pkField)
    {
        super(pkField);
    }
    
    public void setPropertyState(PropertyStateEnum item) {
    	super.setPropertyState(item);
    	if(item == null){
    		setString("propertyState", null);
    	}
    }
}