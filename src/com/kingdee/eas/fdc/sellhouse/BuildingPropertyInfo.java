package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;

public class BuildingPropertyInfo extends AbstractBuildingPropertyInfo implements Serializable 
{
    public BuildingPropertyInfo()
    {
        super();
    }
    protected BuildingPropertyInfo(String pkField)
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