package com.kingdee.eas.fdc.tenancy;

import java.math.BigDecimal;
import java.util.Date;

public interface IRentIncreadedInfo {

	Date getIncreaseDate();

	IncreasedRentTypeEnum getIncreaseType();

	BigDecimal getValue();
}
