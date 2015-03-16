package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;

import com.kingdee.eas.fdc.basecrm.RevListTypeEnum;

public class CusRevListInfo extends AbstractCusRevListInfo implements Serializable 
{
    public CusRevListInfo()
    {
        super();
    }
    protected CusRevListInfo(String pkField)
    {
        super(pkField);
    }
    
    public RevListTypeEnum getRevListTypeEnum() {
    	return RevListTypeEnum.fdcCustomerRev;
    }
    
    public String getDesc() {
    	return "客户预收款明细";
    }
    
}