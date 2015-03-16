package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;

public class BuildingUnitInfo extends AbstractBuildingUnitInfo implements Serializable 
{
    public BuildingUnitInfo()
    {
        super();
    }
    protected BuildingUnitInfo(String pkField)
    {
        super(pkField);
    }
}