package com.kingdee.eas.fdc.market;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class MarketDisplaySetCollection extends AbstractObjectCollection 
{
    public MarketDisplaySetCollection()
    {
        super(MarketDisplaySetInfo.class);
    }
    public boolean add(MarketDisplaySetInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(MarketDisplaySetCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(MarketDisplaySetInfo item)
    {
        return removeObject(item);
    }
    public MarketDisplaySetInfo get(int index)
    {
        return(MarketDisplaySetInfo)getObject(index);
    }
    public MarketDisplaySetInfo get(Object key)
    {
        return(MarketDisplaySetInfo)getObject(key);
    }
    public void set(int index, MarketDisplaySetInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(MarketDisplaySetInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(MarketDisplaySetInfo item)
    {
        return super.indexOf(item);
    }
}