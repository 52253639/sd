package com.kingdee.eas.fdc.tenancy;

import java.util.Date;

public interface IRentFreeInfo {

	Date getFreeStartDate();

	Date getFreeEndDate();

	String getDescription();
	
}
