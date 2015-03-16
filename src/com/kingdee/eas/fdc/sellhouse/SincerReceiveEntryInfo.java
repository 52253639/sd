package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import com.kingdee.eas.fdc.basecrm.RevListTypeEnum;

public class SincerReceiveEntryInfo extends AbstractSincerReceiveEntryInfo implements Serializable,ISHEPayListEntryInfo
{
    public SincerReceiveEntryInfo()
    {
        super();
    }
    protected SincerReceiveEntryInfo(String pkField)
    {
        super(pkField);
    }

    public RevListTypeEnum getRevListTypeEnum() {
    	return RevListTypeEnum.sincerityPur;
    }
    
	public BigDecimal getApAmount() {
		return this.getAppAmount();
	}
	public BigDecimal getActPayAmount() {
		return this.getActRevAmount();
	}
//	public BigDecimal getCanRefundmentAmount() {
//		return this.getLimitAmount();
//	}
//	public BigDecimal getRefundmentAmount() {
//		return this.getHasRefundmentAmount();
//	}
	
	public String getDesc() {
		return "诚意认购应收明细";
	}
	public Timestamp getLastUpdateTime() {
		// TODO Auto-generated method stub
		return null;
	}
	public BigDecimal getCanRefundmentAmount() {
		// TODO Auto-generated method stub
		return null;
	}
	public BigDecimal getRefundmentAmount() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}