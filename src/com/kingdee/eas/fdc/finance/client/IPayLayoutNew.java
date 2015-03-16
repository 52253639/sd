package com.kingdee.eas.fdc.finance.client;

import com.kingdee.eas.fdc.contract.PaymentLayoutInfo;
import com.kingdee.eas.fdc.schedule.ContractAndTaskRelEntryInfo;

public interface IPayLayoutNew {
	public void handleResult(Object o,PaymentLayoutInfo info);
	public void handleResult(ContractAndTaskRelEntryInfo entryInfo);
}
