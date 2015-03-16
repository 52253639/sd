package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;

public class RoomMortagageInfo extends AbstractRoomMortagageInfo implements Serializable 
{
    public RoomMortagageInfo()
    {
        super();
    }
    protected RoomMortagageInfo(String pkField)
    {
        super(pkField);
    }
    
    public String getLogInfo() {
		String retValue = "";
        if(this.getNumber()!= null)
        {
            retValue = this.getNumber();
            if(this.getName()!=null){
            	retValue = retValue + " " + this.getName();
            }
        }
        return retValue;
	}
}