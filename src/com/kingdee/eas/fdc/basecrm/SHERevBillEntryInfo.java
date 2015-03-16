package com.kingdee.eas.fdc.basecrm;

import java.io.Serializable;
import java.math.BigDecimal;

public class SHERevBillEntryInfo extends AbstractSHERevBillEntryInfo implements Serializable 
{
    public SHERevBillEntryInfo()
    {
        super();
    }
    protected SHERevBillEntryInfo(String pkField)
    {
        super(pkField);
    }
    
    
    /**当前剩余金额 = 已收-已退-已转-已对冲 */
    public java.math.BigDecimal getRemainAmount()
    {
        return getAppRevAmount().subtract(getHasTransferAmount()).subtract(getHasMapedAmount());
    }
    
    /**当前收款金额 = 实际收款+冲抵金额 */
    public java.math.BigDecimal getAppRevAmount()
    {
        return getRevAmount().add(getAmount());
    }
    
    public BigDecimal getAmount() {
    	BigDecimal amount = super.getAmount();
    	if(amount==null) amount = new BigDecimal("0");
    	return amount;
    }
    public BigDecimal getHasRefundmentAmount() {
    	BigDecimal retAmount = super.getHasRefundmentAmount();
    	if(retAmount==null) retAmount = new BigDecimal("0");
    	return retAmount;
    }
    
    public BigDecimal getHasTransferAmount() {
    	BigDecimal retAmount = super.getHasTransferAmount();
		if(retAmount==null) retAmount = new BigDecimal("0");
		return retAmount;
    }
    
    public BigDecimal getHasMapedAmount() {
    	BigDecimal retAmount = super.getHasMapedAmount();
		if(retAmount==null) retAmount = new BigDecimal("0");
		return retAmount;    	
    }
    
    public BigDecimal getRevAmount() {
    	BigDecimal retAmount = super.getRevAmount();
		if(retAmount==null) retAmount = new BigDecimal("0");
		return retAmount;  
    }
    
    
}