package com.kingdee.eas.fdc.invite.markesupplier;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class MarketSupplierStockSupplierServiceTypeCollection extends AbstractObjectCollection 
{
    public MarketSupplierStockSupplierServiceTypeCollection()
    {
        super(MarketSupplierStockSupplierServiceTypeInfo.class);
    }
    public boolean add(MarketSupplierStockSupplierServiceTypeInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(MarketSupplierStockSupplierServiceTypeCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(MarketSupplierStockSupplierServiceTypeInfo item)
    {
        return removeObject(item);
    }
    public MarketSupplierStockSupplierServiceTypeInfo get(int index)
    {
        return(MarketSupplierStockSupplierServiceTypeInfo)getObject(index);
    }
    public MarketSupplierStockSupplierServiceTypeInfo get(Object key)
    {
        return(MarketSupplierStockSupplierServiceTypeInfo)getObject(key);
    }
    public void set(int index, MarketSupplierStockSupplierServiceTypeInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(MarketSupplierStockSupplierServiceTypeInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(MarketSupplierStockSupplierServiceTypeInfo item)
    {
        return super.indexOf(item);
    }
}