package com.kingdee.eas.fdc.tenancy;

import java.math.BigDecimal;
import java.util.Date;

import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo;

public interface ITenancyPayListInfo extends IObjectValue{

	void setAppAmount(BigDecimal leaseRent);

	void setLeaseSeq(int leaseSeq);

	void setSeq(int seq);

	void setStartDate(Date startDate);

	void setEndDate(Date endDate);

	void setMoneyDefine(MoneyDefineInfo moneyDefine);

	void setAppPayDate(Date appPayDate);

	MoneyDefineInfo getMoneyDefine();

	int getSeq();

	Date getStartDate();

	Date getEndDate();

	Date getAppPayDate();

	int getLeaseSeq();

	BigDecimal getAppAmount();

	BigDecimal getActAmount();

	Date getActPayDate();

	BigDecimal getCanRefundmentAmount();
	
	String getStrId();

	void setTenEntry(ITenancyEntryInfo tenEntry);
	
}
