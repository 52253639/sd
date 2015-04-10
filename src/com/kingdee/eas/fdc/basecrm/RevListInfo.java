package com.kingdee.eas.fdc.basecrm;

import java.io.Serializable;
import java.math.BigDecimal;

public abstract class RevListInfo extends AbstractRevListInfo implements Serializable,IRevListInfo
{
    public RevListInfo()
    {
        super();
    }
    protected RevListInfo(String pkField)
    {
        super(pkField);
    }
    
    public BigDecimal getFinalAppAmount() {
    	return this.getAppAmount();
    }
    
	public BigDecimal getActRevAmountAfterAdjust(){
		return this.getActRevAmount().subtract(this.getHasAdjustedAmount());
	}
	
	public BigDecimal getRemainAmount(){
		return this.getActRevAmountAfterAdjust().subtract(this.getHasTransferredAmount())
				.subtract(this.getHasRefundmentAmount()).subtract(this.getToFeeAmount());
	}
    
	public BigDecimal getAllRemainAmount() {
		return this.getActRevAmount().subtract(this.getHasToFeeAmount()).subtract(this.getHasTransferredAmount())
				.subtract(this.getHasRefundmentAmount()).subtract(this.getHasAdjustedAmount());
	}
	
	public String getDesc() {
		return "";
	}

	public BigDecimal getRemainLimitAmount() {
		if(isIsRemainCanRefundment()){
			return getRemainAmount();
		}else{
			return this.getLimitAmount().subtract(this.getHasTransferredAmount())
					.subtract(this.getHasRefundmentAmount()).subtract(this.getHasAdjustedAmount());
		}		
	}
	
	public RevListTypeEnum getRevListTypeEnum() {
		return null;
	}
	
	public BigDecimal getAppAmount() {	
		return CRMHelper.getBigDecimal(super.getAppAmount());
	}
	
	public BigDecimal getActRevAmount() {	
		return CRMHelper.getBigDecimal(super.getActRevAmount());
	}
	
	public BigDecimal getHasTransferredAmount() {
		return CRMHelper.getBigDecimal(super.getHasTransferredAmount());
	}
	
	
	public BigDecimal getHasRefundmentAmount() {
		return CRMHelper.getBigDecimal(super.getHasRefundmentAmount());
	}
	
	public BigDecimal getToFeeAmount() {
		return CRMHelper.getBigDecimal(super.getToFeeAmount());
	}
	
	public BigDecimal getHasToFeeAmount() {
		return CRMHelper.getBigDecimal(super.getHasToFeeAmount());
	}
	
	public BigDecimal getLimitAmount() {
		return CRMHelper.getBigDecimal(super.getLimitAmount());
	}
	
	public BigDecimal getHasAdjustedAmount() {
		return CRMHelper.getBigDecimal(super.getHasAdjustedAmount());
	}
	
	public BigDecimal getInvoiceAmount() {
		return CRMHelper.getBigDecimal(super.getInvoiceAmount());
	}
	
}