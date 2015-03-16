package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class HopedUnitPriceCollection extends AbstractObjectCollection 
{
    public HopedUnitPriceCollection()
    {
        super(HopedUnitPriceInfo.class);
    }
    public boolean add(HopedUnitPriceInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(HopedUnitPriceCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(HopedUnitPriceInfo item)
    {
        return removeObject(item);
    }
    public HopedUnitPriceInfo get(int index)
    {
        return(HopedUnitPriceInfo)getObject(index);
    }
    public HopedUnitPriceInfo get(Object key)
    {
        return(HopedUnitPriceInfo)getObject(key);
    }
    public void set(int index, HopedUnitPriceInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(HopedUnitPriceInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(HopedUnitPriceInfo item)
    {
        return super.indexOf(item);
    }
}