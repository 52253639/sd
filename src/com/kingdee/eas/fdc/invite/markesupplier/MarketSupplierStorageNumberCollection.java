package com.kingdee.eas.fdc.invite.markesupplier;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class MarketSupplierStorageNumberCollection extends AbstractObjectCollection 
{
    public MarketSupplierStorageNumberCollection()
    {
        super(MarketSupplierStorageNumberInfo.class);
    }
    public boolean add(MarketSupplierStorageNumberInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(MarketSupplierStorageNumberCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(MarketSupplierStorageNumberInfo item)
    {
        return removeObject(item);
    }
    public MarketSupplierStorageNumberInfo get(int index)
    {
        return(MarketSupplierStorageNumberInfo)getObject(index);
    }
    public MarketSupplierStorageNumberInfo get(Object key)
    {
        return(MarketSupplierStorageNumberInfo)getObject(key);
    }
    public void set(int index, MarketSupplierStorageNumberInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(MarketSupplierStorageNumberInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(MarketSupplierStorageNumberInfo item)
    {
        return super.indexOf(item);
    }
}