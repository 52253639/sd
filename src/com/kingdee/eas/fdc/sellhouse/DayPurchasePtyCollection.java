package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class DayPurchasePtyCollection extends AbstractObjectCollection 
{
    public DayPurchasePtyCollection()
    {
        super(DayPurchasePtyInfo.class);
    }
    public boolean add(DayPurchasePtyInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(DayPurchasePtyCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(DayPurchasePtyInfo item)
    {
        return removeObject(item);
    }
    public DayPurchasePtyInfo get(int index)
    {
        return(DayPurchasePtyInfo)getObject(index);
    }
    public DayPurchasePtyInfo get(Object key)
    {
        return(DayPurchasePtyInfo)getObject(key);
    }
    public void set(int index, DayPurchasePtyInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(DayPurchasePtyInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(DayPurchasePtyInfo item)
    {
        return super.indexOf(item);
    }
}