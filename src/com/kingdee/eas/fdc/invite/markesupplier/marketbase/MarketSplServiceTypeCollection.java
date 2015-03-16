package com.kingdee.eas.fdc.invite.markesupplier.marketbase;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class MarketSplServiceTypeCollection extends AbstractObjectCollection 
{
    public MarketSplServiceTypeCollection()
    {
        super(MarketSplServiceTypeInfo.class);
    }
    public boolean add(MarketSplServiceTypeInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(MarketSplServiceTypeCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(MarketSplServiceTypeInfo item)
    {
        return removeObject(item);
    }
    public MarketSplServiceTypeInfo get(int index)
    {
        return(MarketSplServiceTypeInfo)getObject(index);
    }
    public MarketSplServiceTypeInfo get(Object key)
    {
        return(MarketSplServiceTypeInfo)getObject(key);
    }
    public void set(int index, MarketSplServiceTypeInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(MarketSplServiceTypeInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(MarketSplServiceTypeInfo item)
    {
        return super.indexOf(item);
    }
}