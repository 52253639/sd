package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

import com.kingdee.eas.fdc.basecrm.RevListTypeEnum;

public class PurchasePayListEntryInfo extends AbstractPurchasePayListEntryInfo implements Serializable,ISHEPayListEntryInfo 
{
    public PurchasePayListEntryInfo()
    {
        super();
    }
    protected PurchasePayListEntryInfo(String pkField)
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
		return RevListTypeEnum.purchaseRev;
	}	

	public Date getActPayDate() {
		return super.getActRevDate();
	}
	
	public Date getApDate() {
		return super.getAppDate();
	}
	
	public String getDesc() {
		return this.getDescription();
	}
}