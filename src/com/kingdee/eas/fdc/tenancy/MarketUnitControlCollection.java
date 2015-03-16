package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class MarketUnitControlCollection extends AbstractObjectCollection 
{
    public MarketUnitControlCollection()
    {
        super(MarketUnitControlInfo.class);
    }
    public boolean add(MarketUnitControlInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(MarketUnitControlCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(MarketUnitControlInfo item)
    {
        return removeObject(item);
    }
    public MarketUnitControlInfo get(int index)
    {
        return(MarketUnitControlInfo)getObject(index);
    }
    public MarketUnitControlInfo get(Object key)
    {
        return(MarketUnitControlInfo)getObject(key);
    }
    public void set(int index, MarketUnitControlInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(MarketUnitControlInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(MarketUnitControlInfo item)
    {
        return super.indexOf(item);
    }
}