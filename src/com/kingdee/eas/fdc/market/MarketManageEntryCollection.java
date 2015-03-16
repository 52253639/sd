package com.kingdee.eas.fdc.market;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class MarketManageEntryCollection extends AbstractObjectCollection 
{
    public MarketManageEntryCollection()
    {
        super(MarketManageEntryInfo.class);
    }
    public boolean add(MarketManageEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(MarketManageEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(MarketManageEntryInfo item)
    {
        return removeObject(item);
    }
    public MarketManageEntryInfo get(int index)
    {
        return(MarketManageEntryInfo)getObject(index);
    }
    public MarketManageEntryInfo get(Object key)
    {
        return(MarketManageEntryInfo)getObject(key);
    }
    public void set(int index, MarketManageEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(MarketManageEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(MarketManageEntryInfo item)
    {
        return super.indexOf(item);
    }
}