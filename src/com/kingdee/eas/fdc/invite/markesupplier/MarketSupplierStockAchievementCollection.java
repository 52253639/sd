package com.kingdee.eas.fdc.invite.markesupplier;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class MarketSupplierStockAchievementCollection extends AbstractObjectCollection 
{
    public MarketSupplierStockAchievementCollection()
    {
        super(MarketSupplierStockAchievementInfo.class);
    }
    public boolean add(MarketSupplierStockAchievementInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(MarketSupplierStockAchievementCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(MarketSupplierStockAchievementInfo item)
    {
        return removeObject(item);
    }
    public MarketSupplierStockAchievementInfo get(int index)
    {
        return(MarketSupplierStockAchievementInfo)getObject(index);
    }
    public MarketSupplierStockAchievementInfo get(Object key)
    {
        return(MarketSupplierStockAchievementInfo)getObject(key);
    }
    public void set(int index, MarketSupplierStockAchievementInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(MarketSupplierStockAchievementInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(MarketSupplierStockAchievementInfo item)
    {
        return super.indexOf(item);
    }
}