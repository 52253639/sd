package com.kingdee.eas.fdc.tenancy;

import java.io.Serializable;
import java.math.BigDecimal;

public class NewAttachDealAmountEntryInfo extends AbstractNewAttachDealAmountEntryInfo implements Serializable,IDealAmountInfo 
{
    public NewAttachDealAmountEntryInfo()
    {
        super();
    }
    protected NewAttachDealAmountEntryInfo(String pkField)
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
		// TODO Auto-generated method stub
		
	}
}