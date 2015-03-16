package com.kingdee.eas.fdc.invite.markesupplier;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class MarketSupplierStockAptitudeFileCollection extends AbstractObjectCollection 
{
    public MarketSupplierStockAptitudeFileCollection()
    {
        super(MarketSupplierStockAptitudeFileInfo.class);
    }
    public boolean add(MarketSupplierStockAptitudeFileInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(MarketSupplierStockAptitudeFileCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(MarketSupplierStockAptitudeFileInfo item)
    {
        return removeObject(item);
    }
    public MarketSupplierStockAptitudeFileInfo get(int index)
    {
        return(MarketSupplierStockAptitudeFileInfo)getObject(index);
    }
    public MarketSupplierStockAptitudeFileInfo get(Object key)
    {
        return(MarketSupplierStockAptitudeFileInfo)getObject(key);
    }
    public void set(int index, MarketSupplierStockAptitudeFileInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(MarketSupplierStockAptitudeFileInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(MarketSupplierStockAptitudeFileInfo item)
    {
        return super.indexOf(item);
    }
}