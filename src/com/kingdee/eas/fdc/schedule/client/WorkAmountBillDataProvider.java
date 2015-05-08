package com.kingdee.eas.fdc.schedule.client;

import org.apache.log4j.Logger;

import com.kingdee.eas.fdc.basedata.util.client.RENoteDataProvider;
import com.kingdee.eas.fdc.contract.client.ContractBillEditDataProvider;

public class WorkAmountBillDataProvider extends RENoteDataProvider {

	private static final Logger logger = Logger
			.getLogger(ContractBillEditDataProvider.class);

	public WorkAmountBillDataProvider(String billId) {
		super(billId);
	}

}
