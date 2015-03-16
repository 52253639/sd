package com.kingdee.eas.fdc.invite.markesupplier;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class MarketSupplierStockCollection extends AbstractObjectCollection 
{
    public MarketSupplierStockCollection()
    {
        super(MarketSupplierStockInfo.class);
    }
    public boolean add(MarketSupplierStockInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(MarketSupplierStockCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(MarketSupplierStockInfo item)
    {
        return removeObject(item);
    }
    public MarketSupplierStockInfo get(int index)
    {
        return(MarketSupplierStockInfo)getObject(index);
    }
    public MarketSupplierStockInfo get(Object key)
    {
        return(MarketSupplierStockInfo)getObject(key);
    }
    public void set(int index, MarketSupplierStockInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(MarketSupplierStockInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(MarketSupplierStockInfo item)
    {
        return super.indexOf(item);
    }
}