package com.kingdee.eas.fdc.invite.markesupplier.marketbase;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class MarketSupplierBusinessModeCollection extends AbstractObjectCollection 
{
    public MarketSupplierBusinessModeCollection()
    {
        super(MarketSupplierBusinessModeInfo.class);
    }
    public boolean add(MarketSupplierBusinessModeInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(MarketSupplierBusinessModeCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(MarketSupplierBusinessModeInfo item)
    {
        return removeObject(item);
    }
    public MarketSupplierBusinessModeInfo get(int index)
    {
        return(MarketSupplierBusinessModeInfo)getObject(index);
    }
    public MarketSupplierBusinessModeInfo get(Object key)
    {
        return(MarketSupplierBusinessModeInfo)getObject(key);
    }
    public void set(int index, MarketSupplierBusinessModeInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(MarketSupplierBusinessModeInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(MarketSupplierBusinessModeInfo item)
    {
        return super.indexOf(item);
    }
}