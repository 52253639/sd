package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;

public class RoomInfo extends AbstractRoomInfo implements Serializable 
{
    public RoomInfo()
    {
        super();
    }
    protected RoomInfo(String pkField)
    {
        super(pkField);
    }
    
    public void setChangeState(com.kingdee.eas.fdc.sellhouse.ChangeStateEnum item) {
    	if(item != null){
    		super.setChangeState(item);
    	}
    	else{
    		setString("changeState", null);
    	}
    }
    
    public void setActChangeState(RoomActChangeStateEnum item) {
    	if(item != null){
    		super.setActChangeState(item);
    	}
    	else{
    		setString("actChangeState", null);
    	}
    }
    
    public void setPreChangeState(RoomPreChangeStateEnum item) {
    	if(item != null){
    		super.setPreChangeState(item);
    	}
    	else{
    		setString("preChangeState", null);
    	}
    }
    
    public void setPlanChangeState(RoomPlanChangeStateEnum item) {
    	if(item != null){
    		super.setPlanChangeState(item);
    	}
    	else{
    		setString("planChangeState", null);
    	}
    }
}