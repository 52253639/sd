package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class HopedTotalPricesCollection extends AbstractObjectCollection 
{
    public HopedTotalPricesCollection()
    {
        super(HopedTotalPricesInfo.class);
    }
    public boolean add(HopedTotalPricesInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(HopedTotalPricesCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(HopedTotalPricesInfo item)
    {
        return removeObject(item);
    }
    public HopedTotalPricesInfo get(int index)
    {
        return(HopedTotalPricesInfo)getObject(index);
    }
    public HopedTotalPricesInfo get(Object key)
    {
        return(HopedTotalPricesInfo)getObject(key);
    }
    public void set(int index, HopedTotalPricesInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(HopedTotalPricesInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(HopedTotalPricesInfo item)
    {
        return super.indexOf(item);
    }
}