package com.kingdee.eas.fdc.aimcost;

import java.io.Serializable;

public class AimCostInfo extends AbstractAimCostInfo implements Serializable 
{
    public AimCostInfo()
    {
        super();
    }
    protected AimCostInfo(String pkField)
    {
        super(pkField);
    }
    public String getLogInfo() {
		String retValue = "";
        if(this.getNumber()!= null)
        {
            retValue = this.getNumber();
        }
        return retValue;
	}
}