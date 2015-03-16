package com.kingdee.eas.fdc.basecrm;

import java.io.Serializable;

public class FDCReceivingBillEntryInfo extends AbstractFDCReceivingBillEntryInfo implements Serializable 
{
    public FDCReceivingBillEntryInfo()
    {
        super();
    }
    protected FDCReceivingBillEntryInfo(String pkField)
    {
        super(pkField);
    }
    
    /**
     * 由于预收,直收提交时可能会没有收款明细,在收款单提交时才同步生成收款明细
     * 将收款明细的对象封装在收款单分录中,便于在收款单提交时同步生成收款明细
     * */
    public IRevListInfo getRevListInfo(){
    	return (IRevListInfo) get("revListInfo");
    }
    
    public void setRevListInfo(IRevListInfo revListInfo){
    	put("revListInfo", revListInfo);
    }
}