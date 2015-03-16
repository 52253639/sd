package com.kingdee.eas.fdc.invite.markesupplier;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class MarketSupplierStockLinkPersonCollection extends AbstractObjectCollection 
{
    public MarketSupplierStockLinkPersonCollection()
    {
        super(MarketSupplierStockLinkPersonInfo.class);
    }
    public boolean add(MarketSupplierStockLinkPersonInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(MarketSupplierStockLinkPersonCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(MarketSupplierStockLinkPersonInfo item)
    {
        return removeObject(item);
    }
    public MarketSupplierStockLinkPersonInfo get(int index)
    {
        return(MarketSupplierStockLinkPersonInfo)getObject(index);
    }
    public MarketSupplierStockLinkPersonInfo get(Object key)
    {
        return(MarketSupplierStockLinkPersonInfo)getObject(key);
    }
    public void set(int index, MarketSupplierStockLinkPersonInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(MarketSupplierStockLinkPersonInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(MarketSupplierStockLinkPersonInfo item)
    {
        return super.indexOf(item);
    }
}