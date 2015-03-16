package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import java.util.Map;

public class PurchaseInfo extends AbstractPurchaseInfo implements Serializable 
{
    public PurchaseInfo()
    {
        super();
    }
    protected PurchaseInfo(String pkField)
    {
        super(pkField);
    }
    
    
    public Map getValueMap(){
    	return this.values;
    }
    
    public void setValueMap(Map values){
    	this.values = values;
    }
    
    
}