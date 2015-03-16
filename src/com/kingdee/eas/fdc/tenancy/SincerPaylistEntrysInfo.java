package com.kingdee.eas.fdc.tenancy;

import java.io.Serializable;

import com.kingdee.eas.fdc.basecrm.RevListTypeEnum;

public class SincerPaylistEntrysInfo extends AbstractSincerPaylistEntrysInfo implements Serializable 
{
    public SincerPaylistEntrysInfo()
    {
        super();
        //TODO 租赁这边，先全部默认为剩余金额可退
        this.setIsRemainCanRefundment(true);
    }
    protected SincerPaylistEntrysInfo(String pkField)
    {
        super(pkField);
    }    
    public RevListTypeEnum getRevListTypeEnum()
    {
    	return RevListTypeEnum.sincerobligate;
    }
}