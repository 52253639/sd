package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class DayPurchaseBldCollection extends AbstractObjectCollection 
{
    public DayPurchaseBldCollection()
    {
        super(DayPurchaseBldInfo.class);
    }
    public boolean add(DayPurchaseBldInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(DayPurchaseBldCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(DayPurchaseBldInfo item)
    {
        return removeObject(item);
    }
    public DayPurchaseBldInfo get(int index)
    {
        return(DayPurchaseBldInfo)getObject(index);
    }
    public DayPurchaseBldInfo get(Object key)
    {
        return(DayPurchaseBldInfo)getObject(key);
    }
    public void set(int index, DayPurchaseBldInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(DayPurchaseBldInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(DayPurchaseBldInfo item)
    {
        return super.indexOf(item);
    }
}