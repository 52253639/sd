package com.kingdee.eas.fdc.aimcost;

import java.io.Serializable;

public class PlanIndexEntryInfo extends AbstractPlanIndexEntryInfo implements Serializable 
{
    public PlanIndexEntryInfo()
    {
        super();
    }
    protected PlanIndexEntryInfo(String pkField)
    {
        super(pkField);
    }
    
    public void setType(com.kingdee.eas.fdc.aimcost.PlanIndexTypeEnum item)
    {
    	if(item==null){
    		 setString("type", null);
    	}else{
    		super.setType(item);
    	}
    }
}