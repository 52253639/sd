package com.kingdee.eas.fdc.market;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class MarketManageChargeCollection extends AbstractObjectCollection 
{
    public MarketManageChargeCollection()
    {
        super(MarketManageChargeInfo.class);
    }
    public boolean add(MarketManageChargeInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(MarketManageChargeCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(MarketManageChargeInfo item)
    {
        return removeObject(item);
    }
    public MarketManageChargeInfo get(int index)
    {
        return(MarketManageChargeInfo)getObject(index);
    }
    public MarketManageChargeInfo get(Object key)
    {
        return(MarketManageChargeInfo)getObject(key);
    }
    public void set(int index, MarketManageChargeInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(MarketManageChargeInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(MarketManageChargeInfo item)
    {
        return super.indexOf(item);
    }
}