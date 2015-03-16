package com.kingdee.eas.fdc.market;

import java.io.Serializable;

public class StoreItemInfo extends AbstractStoreItemInfo implements Serializable 
{
    public StoreItemInfo()
    {
        super();
    }
    protected StoreItemInfo(String pkField)
    {
        super(pkField);
    }
    
    
    /**
     * Object: 题库管理 's 选项数据 property 
     */
    public void setOptions(StoreItemCollection items)
    {
    	put("options", items);
    }
    
}