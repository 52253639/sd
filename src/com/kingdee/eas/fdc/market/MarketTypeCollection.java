package com.kingdee.eas.fdc.market;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class MarketTypeCollection extends AbstractObjectCollection 
{
    public MarketTypeCollection()
    {
        super(MarketTypeInfo.class);
    }
    public boolean add(MarketTypeInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(MarketTypeCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(MarketTypeInfo item)
    {
        return removeObject(item);
    }
    public MarketTypeInfo get(int index)
    {
        return(MarketTypeInfo)getObject(index);
    }
    public MarketTypeInfo get(Object key)
    {
        return(MarketTypeInfo)getObject(key);
    }
    public void set(int index, MarketTypeInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(MarketTypeInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(MarketTypeInfo item)
    {
        return super.indexOf(item);
    }
}