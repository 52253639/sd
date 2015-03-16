package com.kingdee.eas.fdc.tenancy;

import java.math.BigDecimal;
import java.util.Date;

import com.kingdee.bos.dao.IObjectCollection;

/**
 * 可租赁的对象分录
 * 需要租赁的对象均需要实现该接口
 * */
public interface ITenancyEntryInfo {

	void setDepositAmount(BigDecimal value);

	void setFirstPayAmount(BigDecimal value);

	void setDealRentType(RentTypeEnum rentType);

	//由于返回值类型不同，该方法加个_后缀
	IObjectCollection getDealAmounts_();
	
	//适用TenancyRoom的方法
	void setTenancyModel(TenancyModeEnum tenModel);
	//适用TenancyRoom的方法
	BigDecimal getBuildingArea();
	BigDecimal getRoomArea();

	void setDealRent(BigDecimal amount);

	void setDealRentPrice(BigDecimal price);

	IObjectCollection getPayList();

	HandleStateEnum getHandleState();
	
	String getStrId();
	
	String getLongNumber();
	
	Date getActDeliveryDate();

	BigDecimal getDepositAmount();

	void setTotalRent(BigDecimal oneEntryTotalRent);

	RentTypeEnum getDealRentType();

	TenancyModeEnum getTenancyModel();

	BigDecimal getDealRent();
	
	BigDecimal getRentArea();
	
	BigDecimal getDealRentPrice();
	BigDecimal getDayPrice_();
}
