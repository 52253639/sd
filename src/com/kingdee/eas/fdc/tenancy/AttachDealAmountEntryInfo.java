package com.kingdee.eas.fdc.tenancy;

import java.io.Serializable;
import java.math.BigDecimal;

public class AttachDealAmountEntryInfo extends AbstractAttachDealAmountEntryInfo implements Serializable,IDealAmountInfo 
{
    public AttachDealAmountEntryInfo()
    {
        super();
    }
    protected AttachDealAmountEntryInfo(String pkField)
    {
        super(pkField);
    }
	public BigDecimal getPriceAmount() {
		return null;
	}
	public TenancyRoomEntryInfo getTenancyRoom() {
		return null;
	}
	public void setPriceAmount(BigDecimal price) {
	}
	public void setTenancyEntry(ITenancyEntryInfo tenEntry) {
		this.setTenancyAttach((TenAttachResourceEntryInfo) tenEntry);
	}
}