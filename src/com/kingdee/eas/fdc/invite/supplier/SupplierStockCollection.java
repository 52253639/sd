package com.kingdee.eas.fdc.invite.supplier;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class SupplierStockCollection extends AbstractObjectCollection 
{
    public SupplierStockCollection()
    {
        super(SupplierStockInfo.class);
    }
    public boolean add(SupplierStockInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(SupplierStockCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(SupplierStockInfo item)
    {
        return removeObject(item);
    }
    public SupplierStockInfo get(int index)
    {
        return(SupplierStockInfo)getObject(index);
    }
    public SupplierStockInfo get(Object key)
    {
        return(SupplierStockInfo)getObject(key);
    }
    public void set(int index, SupplierStockInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(SupplierStockInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(SupplierStockInfo item)
    {
        return super.indexOf(item);
    }
}