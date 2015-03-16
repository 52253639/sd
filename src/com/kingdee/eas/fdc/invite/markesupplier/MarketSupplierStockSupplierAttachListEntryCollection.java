package com.kingdee.eas.fdc.invite.markesupplier;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class MarketSupplierStockSupplierAttachListEntryCollection extends AbstractObjectCollection 
{
    public MarketSupplierStockSupplierAttachListEntryCollection()
    {
        super(MarketSupplierStockSupplierAttachListEntryInfo.class);
    }
    public boolean add(MarketSupplierStockSupplierAttachListEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(MarketSupplierStockSupplierAttachListEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(MarketSupplierStockSupplierAttachListEntryInfo item)
    {
        return removeObject(item);
    }
    public MarketSupplierStockSupplierAttachListEntryInfo get(int index)
    {
        return(MarketSupplierStockSupplierAttachListEntryInfo)getObject(index);
    }
    public MarketSupplierStockSupplierAttachListEntryInfo get(Object key)
    {
        return(MarketSupplierStockSupplierAttachListEntryInfo)getObject(key);
    }
    public void set(int index, MarketSupplierStockSupplierAttachListEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(MarketSupplierStockSupplierAttachListEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(MarketSupplierStockSupplierAttachListEntryInfo item)
    {
        return super.indexOf(item);
    }
}