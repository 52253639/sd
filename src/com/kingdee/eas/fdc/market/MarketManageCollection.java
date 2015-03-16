package com.kingdee.eas.fdc.market;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class MarketManageCollection extends AbstractObjectCollection 
{
    public MarketManageCollection()
    {
        super(MarketManageInfo.class);
    }
    public boolean add(MarketManageInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(MarketManageCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(MarketManageInfo item)
    {
        return removeObject(item);
    }
    public MarketManageInfo get(int index)
    {
        return(MarketManageInfo)getObject(index);
    }
    public MarketManageInfo get(Object key)
    {
        return(MarketManageInfo)getObject(key);
    }
    public void set(int index, MarketManageInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(MarketManageInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(MarketManageInfo item)
    {
        return super.indexOf(item);
    }
}