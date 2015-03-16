package com.kingdee.eas.fdc.invite.markesupplier.marketbase;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class MarketSupplierAttachListCollection extends AbstractObjectCollection 
{
    public MarketSupplierAttachListCollection()
    {
        super(MarketSupplierAttachListInfo.class);
    }
    public boolean add(MarketSupplierAttachListInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(MarketSupplierAttachListCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(MarketSupplierAttachListInfo item)
    {
        return removeObject(item);
    }
    public MarketSupplierAttachListInfo get(int index)
    {
        return(MarketSupplierAttachListInfo)getObject(index);
    }
    public MarketSupplierAttachListInfo get(Object key)
    {
        return(MarketSupplierAttachListInfo)getObject(key);
    }
    public void set(int index, MarketSupplierAttachListInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(MarketSupplierAttachListInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(MarketSupplierAttachListInfo item)
    {
        return super.indexOf(item);
    }
}