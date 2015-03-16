package com.kingdee.eas.fdc.invite.markesupplier;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class MarketSupplierStockSupplierSplAreaEntryCollection extends AbstractObjectCollection 
{
    public MarketSupplierStockSupplierSplAreaEntryCollection()
    {
        super(MarketSupplierStockSupplierSplAreaEntryInfo.class);
    }
    public boolean add(MarketSupplierStockSupplierSplAreaEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(MarketSupplierStockSupplierSplAreaEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(MarketSupplierStockSupplierSplAreaEntryInfo item)
    {
        return removeObject(item);
    }
    public MarketSupplierStockSupplierSplAreaEntryInfo get(int index)
    {
        return(MarketSupplierStockSupplierSplAreaEntryInfo)getObject(index);
    }
    public MarketSupplierStockSupplierSplAreaEntryInfo get(Object key)
    {
        return(MarketSupplierStockSupplierSplAreaEntryInfo)getObject(key);
    }
    public void set(int index, MarketSupplierStockSupplierSplAreaEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(MarketSupplierStockSupplierSplAreaEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(MarketSupplierStockSupplierSplAreaEntryInfo item)
    {
        return super.indexOf(item);
    }
}