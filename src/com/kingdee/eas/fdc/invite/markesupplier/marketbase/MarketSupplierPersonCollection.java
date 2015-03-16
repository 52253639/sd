package com.kingdee.eas.fdc.invite.markesupplier.marketbase;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class MarketSupplierPersonCollection extends AbstractObjectCollection 
{
    public MarketSupplierPersonCollection()
    {
        super(MarketSupplierPersonInfo.class);
    }
    public boolean add(MarketSupplierPersonInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(MarketSupplierPersonCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(MarketSupplierPersonInfo item)
    {
        return removeObject(item);
    }
    public MarketSupplierPersonInfo get(int index)
    {
        return(MarketSupplierPersonInfo)getObject(index);
    }
    public MarketSupplierPersonInfo get(Object key)
    {
        return(MarketSupplierPersonInfo)getObject(key);
    }
    public void set(int index, MarketSupplierPersonInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(MarketSupplierPersonInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(MarketSupplierPersonInfo item)
    {
        return super.indexOf(item);
    }
}