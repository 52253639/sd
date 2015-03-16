package com.kingdee.eas.fdc.invite.markesupplier.marketbase;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class MarketSupplierFileTypCollection extends AbstractObjectCollection 
{
    public MarketSupplierFileTypCollection()
    {
        super(MarketSupplierFileTypInfo.class);
    }
    public boolean add(MarketSupplierFileTypInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(MarketSupplierFileTypCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(MarketSupplierFileTypInfo item)
    {
        return removeObject(item);
    }
    public MarketSupplierFileTypInfo get(int index)
    {
        return(MarketSupplierFileTypInfo)getObject(index);
    }
    public MarketSupplierFileTypInfo get(Object key)
    {
        return(MarketSupplierFileTypInfo)getObject(key);
    }
    public void set(int index, MarketSupplierFileTypInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(MarketSupplierFileTypInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(MarketSupplierFileTypInfo item)
    {
        return super.indexOf(item);
    }
}