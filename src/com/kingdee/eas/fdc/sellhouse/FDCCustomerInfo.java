package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;

public class FDCCustomerInfo extends AbstractFDCCustomerInfo implements Serializable 
{
    public FDCCustomerInfo()
    {
        super();
    }
    protected FDCCustomerInfo(String pkField)
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