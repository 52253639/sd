package com.kingdee.eas.fdc.sellhouse;

import java.math.BigDecimal;
import java.sql.Timestamp;

import com.kingdee.bos.util.BOSUuid;

public interface ISHEPayListEntryInfo {

	BOSUuid getId();

	MoneyDefineInfo getMoneyDefine();

	BigDecimal getCanRefundmentAmount();

	BigDecimal getRefundmentAmount();

	BigDecimal getActPayAmount();

	BigDecimal getApAmount();

	Timestamp getLastUpdateTime();
	
}
