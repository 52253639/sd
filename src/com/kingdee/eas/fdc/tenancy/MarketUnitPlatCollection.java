package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class MarketUnitPlatCollection extends AbstractObjectCollection 
{
    public MarketUnitPlatCollection()
    {
        super(MarketUnitPlatInfo.class);
    }
    public boolean add(MarketUnitPlatInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(MarketUnitPlatCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(MarketUnitPlatInfo item)
    {
        return removeObject(item);
    }
    public MarketUnitPlatInfo get(int index)
    {
        return(MarketUnitPlatInfo)getObject(index);
    }
    public MarketUnitPlatInfo get(Object key)
    {
        return(MarketUnitPlatInfo)getObject(key);
    }
    public void set(int index, MarketUnitPlatInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(MarketUnitPlatInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(MarketUnitPlatInfo item)
    {
        return super.indexOf(item);
    }
}