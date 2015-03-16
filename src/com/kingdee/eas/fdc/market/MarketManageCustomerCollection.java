package com.kingdee.eas.fdc.market;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class MarketManageCustomerCollection extends AbstractObjectCollection 
{
    public MarketManageCustomerCollection()
    {
        super(MarketManageCustomerInfo.class);
    }
    public boolean add(MarketManageCustomerInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(MarketManageCustomerCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(MarketManageCustomerInfo item)
    {
        return removeObject(item);
    }
    public MarketManageCustomerInfo get(int index)
    {
        return(MarketManageCustomerInfo)getObject(index);
    }
    public MarketManageCustomerInfo get(Object key)
    {
        return(MarketManageCustomerInfo)getObject(key);
    }
    public void set(int index, MarketManageCustomerInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(MarketManageCustomerInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(MarketManageCustomerInfo item)
    {
        return super.indexOf(item);
    }
}