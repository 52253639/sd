package com.kingdee.eas.fdc.basecrm;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class MarketUnitSellProCollection extends AbstractObjectCollection 
{
    public MarketUnitSellProCollection()
    {
        super(MarketUnitSellProInfo.class);
    }
    public boolean add(MarketUnitSellProInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(MarketUnitSellProCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(MarketUnitSellProInfo item)
    {
        return removeObject(item);
    }
    public MarketUnitSellProInfo get(int index)
    {
        return(MarketUnitSellProInfo)getObject(index);
    }
    public MarketUnitSellProInfo get(Object key)
    {
        return(MarketUnitSellProInfo)getObject(key);
    }
    public void set(int index, MarketUnitSellProInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(MarketUnitSellProInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(MarketUnitSellProInfo item)
    {
        return super.indexOf(item);
    }
}