package com.kingdee.eas.fdc.tenancy;

import java.io.Serializable;

public class OldDealAmountEntryInfo extends AbstractOldDealAmountEntryInfo implements Serializable,IDealAmountInfo
{
    public OldDealAmountEntryInfo()
    {
        super();
    }
    protected OldDealAmountEntryInfo(String pkField)
    {
        super(pkField);
    }
	public void setTenancyEntry(ITenancyEntryInfo tenEntry) {
		// TODO Auto-generated method stub
	}
}