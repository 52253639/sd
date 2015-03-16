package com.kingdee.eas.fdc.invite.markesupplier.marketbase;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class MarketLevelSetUpCollection extends AbstractObjectCollection 
{
    public MarketLevelSetUpCollection()
    {
        super(MarketLevelSetUpInfo.class);
    }
    public boolean add(MarketLevelSetUpInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(MarketLevelSetUpCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(MarketLevelSetUpInfo item)
    {
        return removeObject(item);
    }
    public MarketLevelSetUpInfo get(int index)
    {
        return(MarketLevelSetUpInfo)getObject(index);
    }
    public MarketLevelSetUpInfo get(Object key)
    {
        return(MarketLevelSetUpInfo)getObject(key);
    }
    public void set(int index, MarketLevelSetUpInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(MarketLevelSetUpInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(MarketLevelSetUpInfo item)
    {
        return super.indexOf(item);
    }
}