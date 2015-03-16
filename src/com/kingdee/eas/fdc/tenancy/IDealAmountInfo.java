package com.kingdee.eas.fdc.tenancy;

import java.math.BigDecimal;
import java.util.Date;

import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo;


public interface IDealAmountInfo extends IObjectValue{

    Date getStartDate();
    
    Date getEndDate();

    BigDecimal getAmount();

    RentTypeEnum getRentType();

    boolean isIsHandwork();

    BigDecimal getPriceAmount();

    TenancyRoomEntryInfo getTenancyRoom();

    MoneyDefineInfo getMoneyDefine();

	void setRentType(RentTypeEnum rentType);

	void setAmount(BigDecimal amount);

	void setPriceAmount(BigDecimal price);

	void setTenancyEntry(ITenancyEntryInfo tenEntry);

	void setIsHandwork(boolean isHandwork);

	void setMoneyDefine(MoneyDefineInfo rentMoney);

	void setStartDate(Date date);

	void setEndDate(Date date);
    
}
