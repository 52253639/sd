package com.kingdee.eas.fdc.aimcost;

import java.io.Serializable;

import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.fdc.basedata.CostAccountFactory;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;

public class DynCostSnapShotInfo extends AbstractDynCostSnapShotInfo implements Serializable 
{
    public DynCostSnapShotInfo()
    {
        super();
    }
    protected DynCostSnapShotInfo(String pkField)
    {
        super(pkField);
    }
    public String getLogInfo() {
    	String retValue = "";
    	if(this.getCostAcctLgNumber()!= null)
    	{       	
    		retValue = this.getCostAcctLgNumber();       			
    	}
    	return retValue;
    }
}