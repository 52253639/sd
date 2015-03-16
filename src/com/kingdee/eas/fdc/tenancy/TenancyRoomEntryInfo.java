package com.kingdee.eas.fdc.tenancy;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.kingdee.bos.dao.IObjectCollection;

public class TenancyRoomEntryInfo extends AbstractTenancyRoomEntryInfo implements Serializable,ITenancyEntryInfo 
{
    public TenancyRoomEntryInfo()
    {
        super();
    }
    protected TenancyRoomEntryInfo(String pkField)
    {
        super(pkField);
    }
	public IObjectCollection getDealAmounts_() {
		return getDealAmounts();
	}
	public void setDealRent(BigDecimal amount) {
		this.setDealRoomRent(amount);
	}
	public void setDealRentPrice(BigDecimal price) {
		this.setDealRoomRentPrice(price);
	}
	public IObjectCollection getPayList() {
		return getRoomPayList();
	}
	public String getStrId() {
		return this.getId().toString();
	}
	public Date getActDeliveryDate() {
		return this.getActDeliveryRoomDate();
	}
	public String getLongNumber() {
		return this.getRoomLongNum();
	}
	public void setTotalRent(BigDecimal oneEntryTotalRent) {
		this.setRoomTotalRent(oneEntryTotalRent);
	}
	public BigDecimal getDealRentPrice()
	{
		return this.getDealRoomRentPrice();
	}
	public BigDecimal getDealRent() {
		return this.getDealRoomRent();
	}
	public BigDecimal getRentArea() {
		//TODO
		return null;
	}
	public BigDecimal getDayPrice_() {
		return super.getDayPrice();
	}
}