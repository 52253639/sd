package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import java.math.BigDecimal;

import com.kingdee.eas.fdc.basecrm.RevListTypeEnum;

public class PurchaseElsePayListEntryInfo extends AbstractPurchaseElsePayListEntryInfo implements Serializable,ISHEPayListEntryInfo 
{
    public PurchaseElsePayListEntryInfo()
    {
        super();
    }
    protected PurchaseElsePayListEntryInfo(String pkField)
    {
        super(pkField);
    }
	public BigDecimal getActPayAmount() {
		return this.getActRevAmount();
	}
	public BigDecimal getApAmount() {
		return this.getAppAmount();
	}
	public BigDecimal getCanRefundmentAmount() {
		return this.getLimitAmount();
	}
	public BigDecimal getRefundmentAmount() {
		return this.getHasRefundmentAmount();
	}
	public RevListTypeEnum getRevListTypeEnum() {
		return RevListTypeEnum.purElseRev;
	}
	
	public String getDesc() {
		return this.getDescription();
	}
	
}