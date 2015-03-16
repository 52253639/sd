package com.kingdee.eas.fdc.tenancy;

import java.io.Serializable;

public class TenAttachResourcePayListEntryInfo extends AbstractTenAttachResourcePayListEntryInfo implements Serializable,ITenancyPayListInfo 
{
    public TenAttachResourcePayListEntryInfo()
    {
        super();
    }
    protected TenAttachResourcePayListEntryInfo(String pkField)
    {
        super(pkField);
    }
	public String getStrId() {
		return this.getId().toString();
	}
	public void setTenEntry(ITenancyEntryInfo tenEntry) {
		this.setTenAttachRes((TenAttachResourceEntryInfo) tenEntry);
	}
}