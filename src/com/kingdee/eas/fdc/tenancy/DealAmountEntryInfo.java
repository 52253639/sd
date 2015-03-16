package com.kingdee.eas.fdc.tenancy;

import java.io.Serializable;

public class DealAmountEntryInfo extends AbstractDealAmountEntryInfo implements Serializable,IDealAmountInfo 
{
    public DealAmountEntryInfo()
    {
        super();
    }
    protected DealAmountEntryInfo(String pkField)
    {
        super(pkField);
    }
	public void setTenancyEntry(ITenancyEntryInfo tenEntry) {
		this.setTenancyRoom((TenancyRoomEntryInfo) tenEntry);
	}
}