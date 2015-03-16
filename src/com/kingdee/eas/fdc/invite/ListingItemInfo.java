package com.kingdee.eas.fdc.invite;

import java.io.Serializable;

import com.kingdee.bos.ctrl.kdf.export.IExportObject;

public class ListingItemInfo extends AbstractListingItemInfo implements Serializable,IExportObject
{
    public ListingItemInfo()
    {
        super();
    }
    protected ListingItemInfo(String pkField)
    {
        super(pkField);
    }
	public Object getContent() {
		// TODO 自动生成方法存根
		return getName();
	}
	public String getFormatString() {
		// TODO 自动生成方法存根
		return null;
	}
}