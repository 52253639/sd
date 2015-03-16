package com.kingdee.eas.fdc.sellhouse.client;

import java.math.BigDecimal;
import java.util.Date;

import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.sellhouse.SHEDataTypeEnum;

public class SHEDataGetter {
	public SHEDataGetter() {
	}

	public BigDecimal getSHEData(String[] projectIds, String[] buildingIds,
			String buildingPropertyIds, Date beginDate, Date endDate,
			SHEDataTypeEnum dataType) {
		return FDCHelper.ZERO;
	}
}
