package com.kingdee.eas.fdc.tenancy;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.kingdee.bos.dao.IObjectCollection;

public class TenAttachResourceEntryInfo extends AbstractTenAttachResourceEntryInfo implements Serializable,ITenancyEntryInfo 
{
    public TenAttachResourceEntryInfo()
    {
        super();
    }
    protected TenAttachResourceEntryInfo(String pkField)
    {
        super(pkField);
    }
	public IObjectCollection getDealAmounts_() {
		return getDealAmounts();
	}
	public void setTenancyModel(TenancyModeEnum tenModel) {
		
	}
	public BigDecimal getBuildingArea() {
		return null;
	}
	public void setDealRent(BigDecimal amount) {
		this.setDealAttachResRent(amount);
	}
	public void setDealRentPrice(BigDecimal price) {
		this.setDealAttachResRentPrice(price);
	}
	public BigDecimal getRoomArea() {
		return null;
	}
	public IObjectCollection getPayList() {
		return this.getAttachResPayList();
	}
	public String getStrId() {
		return this.getId().toString();
	}
	public Date getActDeliveryDate() {
		return this.getActDeliveryAttachResDate();
	}
	public String getLongNumber() {
		return this.getAttachLongNum();
	}
	public void setTotalRent(BigDecimal oneEntryTotalRent) {
		// TODO Auto-generated method stub
		
	}
	public BigDecimal getDealRent() {
		// TODO Auto-generated method stub
		return null;
	}
	public BigDecimal getRentArea() {
		// TODO Auto-generated method stub
		return null;
	}
	public TenancyModeEnum getTenancyModel() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public BigDecimal getDealRentPrice()
	{
		return this.getDealAttachResRentPrice();
	}
	public BigDecimal getDayPrice_() {
		// TODO Auto-generated method stub
		return null;
	}
}