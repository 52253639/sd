package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class PriceAdjustCollection extends AbstractObjectCollection 
{
    public PriceAdjustCollection()
    {
        super(PriceAdjustInfo.class);
    }
    public boolean add(PriceAdjustInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(PriceAdjustCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(PriceAdjustInfo item)
    {
        return removeObject(item);
    }
    public PriceAdjustInfo get(int index)
    {
        return(PriceAdjustInfo)getObject(index);
    }
    public PriceAdjustInfo get(Object key)
    {
        return(PriceAdjustInfo)getObject(key);
    }
    public void set(int index, PriceAdjustInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(PriceAdjustInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(PriceAdjustInfo item)
    {
        return super.indexOf(item);
    }
}