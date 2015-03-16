package com.kingdee.eas.fdc.tenancy;

import java.io.Serializable;

public class NewDealAmountEntryInfo extends AbstractNewDealAmountEntryInfo implements Serializable,IDealAmountInfo
{
    public NewDealAmountEntryInfo()
    {
        super();
    }
    protected NewDealAmountEntryInfo(String pkField)
    {
        super(pkField);
    }
	public void setTenancyEntry(ITenancyEntryInfo tenEntry) {
		// TODO Auto-generated method stub
		
	}
}