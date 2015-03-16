package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;

public class BuildingFloorEntryInfo extends AbstractBuildingFloorEntryInfo implements Serializable 
{
    public BuildingFloorEntryInfo()
    {
        super();
    }
    protected BuildingFloorEntryInfo(String pkField)
    {
        super(pkField);
    }
	public String toString() {
		return new Integer(this.getFloor()).toString();
	}
    
}