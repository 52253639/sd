package com.kingdee.eas.fdc.invite.markesupplier;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class MarketSupplierStockYearSaleCollection extends AbstractObjectCollection 
{
    public MarketSupplierStockYearSaleCollection()
    {
        super(MarketSupplierStockYearSaleInfo.class);
    }
    public boolean add(MarketSupplierStockYearSaleInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(MarketSupplierStockYearSaleCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(MarketSupplierStockYearSaleInfo item)
    {
        return removeObject(item);
    }
    public MarketSupplierStockYearSaleInfo get(int index)
    {
        return(MarketSupplierStockYearSaleInfo)getObject(index);
    }
    public MarketSupplierStockYearSaleInfo get(Object key)
    {
        return(MarketSupplierStockYearSaleInfo)getObject(key);
    }
    public void set(int index, MarketSupplierStockYearSaleInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(MarketSupplierStockYearSaleInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(MarketSupplierStockYearSaleInfo item)
    {
        return super.indexOf(item);
    }
}