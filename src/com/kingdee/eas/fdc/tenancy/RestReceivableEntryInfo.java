package com.kingdee.eas.fdc.tenancy;

import java.io.Serializable;

public class RestReceivableEntryInfo extends AbstractRestReceivableEntryInfo implements Serializable 
{
    public RestReceivableEntryInfo()
    {
        super();
    }
    protected RestReceivableEntryInfo(String pkField)
    {
        super(pkField);
    }
    
    public TenBillOtherPayInfo readData(RestReceivableEntryInfo model){
    	TenBillOtherPayInfo objValue = new TenBillOtherPayInfo(); 
    	objValue.setMoneyDefine(model.getMoneyDefine());
    	objValue.setAppDate(model.getAppDate());
    	objValue.setAppAmount(model.getAppAmount());
    	objValue.setCurrency(model.getCurrency());
    	objValue.setActRevDate(model.getActRevDate());
    	objValue.setActRevAmount(model.getActRevAmount());
    	objValue.setDescription(model.getRemark());
    	if(model.getTenancyBillEntry()!=null){
    		objValue.setId(model.getTenancyBillEntry().getId());
    	}
    	return objValue;
    }
}