package com.kingdee.eas.fdc.invite.markesupplier.marketbase;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class MarketSplAreaCollection extends AbstractObjectCollection 
{
    public MarketSplAreaCollection()
    {
        super(MarketSplAreaInfo.class);
    }
    public boolean add(MarketSplAreaInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(MarketSplAreaCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(MarketSplAreaInfo item)
    {
        return removeObject(item);
    }
    public MarketSplAreaInfo get(int index)
    {
        return(MarketSplAreaInfo)getObject(index);
    }
    public MarketSplAreaInfo get(Object key)
    {
        return(MarketSplAreaInfo)getObject(key);
    }
    public void set(int index, MarketSplAreaInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(MarketSplAreaInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(MarketSplAreaInfo item)
    {
        return super.indexOf(item);
    }
}