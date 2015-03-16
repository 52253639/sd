package com.kingdee.eas.fdc.tenancy;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.kingdee.eas.fdc.basecrm.IRevListInfo;
import com.kingdee.eas.fdc.basecrm.RevListTypeEnum;

public class TenancyRoomPayListEntryInfo extends AbstractTenancyRoomPayListEntryInfo implements Serializable,ITenancyPayListInfo,IRevListInfo 
{
    public TenancyRoomPayListEntryInfo()
    {
        super();
        //TODO 租赁这边，先全部默认为剩余金额可退
        this.setIsRemainCanRefundment(true);
    }
    protected TenancyRoomPayListEntryInfo(String pkField)
    {
        super(pkField);
    }
    
	public RevListTypeEnum getRevListTypeEnum() {
		return RevListTypeEnum.tenRoomRev;
	}
    
	public String getStrId() {
		return this.getId().toString();
	}
	public void setTenEntry(ITenancyEntryInfo tenEntry) {
		this.setTenRoom((TenancyRoomEntryInfo) tenEntry);
	}
	
	public BigDecimal getActAmount() {
		return this.getActRevAmount();
	}
	public Date getAppPayDate() {
		return this.getAppDate();
	}
	public BigDecimal getCanRefundmentAmount() {
		return this.getLimitAmount();
	}
	public BigDecimal getHasRefundmentAmount() {
		return super.getHasRefundmentAmount();
	}
	public void setAppPayDate(Date appPayDate) {
		this.setAppDate(appPayDate);
	}
	public Date getActPayDate() {
		return this.getActRevDate();
	}
}