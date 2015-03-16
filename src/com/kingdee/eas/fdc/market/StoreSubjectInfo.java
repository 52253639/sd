package com.kingdee.eas.fdc.market;

import java.io.Serializable;

public class StoreSubjectInfo extends AbstractStoreSubjectInfo implements Serializable 
{
    public StoreSubjectInfo()
    {
        super();
    }
    protected StoreSubjectInfo(String pkField)
    {
        super(pkField);
    }
    
    /**
     * Object: 题库管理 's 分组数据 property 
     */
    public void setItems(StoreItemCollection items)
    {
    	put("items", items);
    }
    
    
}