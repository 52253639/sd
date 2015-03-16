package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;

public class BuildingInfo extends AbstractBuildingInfo implements Serializable 
{
    public BuildingInfo()
    {
        super();
    }
    protected BuildingInfo(String pkField)
    {
        super(pkField);
    }
}