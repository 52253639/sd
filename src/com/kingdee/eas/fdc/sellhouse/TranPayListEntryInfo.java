package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import java.math.BigDecimal;

public class TranPayListEntryInfo extends AbstractTranPayListEntryInfo implements Serializable 
{
    public TranPayListEntryInfo()
    {
        super();
    }
    protected TranPayListEntryInfo(String pkField)
    {
        super(pkField);
    }
    
    public BigDecimal getAppAmount() {
    	BigDecimal retAmount = super.getAppAmount();
    	if(retAmount==null) retAmount = new BigDecimal("0"); 
    	return retAmount;
    }
    
    public BigDecimal getActRevAmount() {
        BigDecimal retAmount = super.getActRevAmount();
    	if(retAmount==null) retAmount = new BigDecimal("0"); 
    	return retAmount;
    }
    
    public BigDecimal getUnPayAmount(){
    	BigDecimal unPayAmount = getAppAmount().subtract(getActRevAmount());
//    	if(unPayAmount.compareTo(new BigDecimal("0"))<0) unPayAmount = new BigDecimal("0");
    	return unPayAmount;
    }
    
}