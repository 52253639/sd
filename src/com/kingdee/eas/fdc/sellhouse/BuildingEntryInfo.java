package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;

public class BuildingEntryInfo extends AbstractBuildingEntryInfo implements Serializable 
{
    public BuildingEntryInfo()
    {
        super();
    }
    protected BuildingEntryInfo(String pkField)
    {
        super(pkField);
    }
}