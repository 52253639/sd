package com.kingdee.eas.fdc.market;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class MarketManageMediaCollection extends AbstractObjectCollection 
{
    public MarketManageMediaCollection()
    {
        super(MarketManageMediaInfo.class);
    }
    public boolean add(MarketManageMediaInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(MarketManageMediaCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(MarketManageMediaInfo item)
    {
        return removeObject(item);
    }
    public MarketManageMediaInfo get(int index)
    {
        return(MarketManageMediaInfo)getObject(index);
    }
    public MarketManageMediaInfo get(Object key)
    {
        return(MarketManageMediaInfo)getObject(key);
    }
    public void set(int index, MarketManageMediaInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(MarketManageMediaInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(MarketManageMediaInfo item)
    {
        return super.indexOf(item);
    }
}