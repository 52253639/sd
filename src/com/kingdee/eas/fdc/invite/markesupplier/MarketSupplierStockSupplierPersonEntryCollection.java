package com.kingdee.eas.fdc.invite.markesupplier;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class MarketSupplierStockSupplierPersonEntryCollection extends AbstractObjectCollection 
{
    public MarketSupplierStockSupplierPersonEntryCollection()
    {
        super(MarketSupplierStockSupplierPersonEntryInfo.class);
    }
    public boolean add(MarketSupplierStockSupplierPersonEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(MarketSupplierStockSupplierPersonEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(MarketSupplierStockSupplierPersonEntryInfo item)
    {
        return removeObject(item);
    }
    public MarketSupplierStockSupplierPersonEntryInfo get(int index)
    {
        return(MarketSupplierStockSupplierPersonEntryInfo)getObject(index);
    }
    public MarketSupplierStockSupplierPersonEntryInfo get(Object key)
    {
        return(MarketSupplierStockSupplierPersonEntryInfo)getObject(key);
    }
    public void set(int index, MarketSupplierStockSupplierPersonEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(MarketSupplierStockSupplierPersonEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(MarketSupplierStockSupplierPersonEntryInfo item)
    {
        return super.indexOf(item);
    }
}