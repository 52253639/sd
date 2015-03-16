package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class SellOrderPlanEntryCollection extends AbstractObjectCollection 
{
    public SellOrderPlanEntryCollection()
    {
        super(SellOrderPlanEntryInfo.class);
    }
    public boolean add(SellOrderPlanEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(SellOrderPlanEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(SellOrderPlanEntryInfo item)
    {
        return removeObject(item);
    }
    public SellOrderPlanEntryInfo get(int index)
    {
        return(SellOrderPlanEntryInfo)getObject(index);
    }
    public SellOrderPlanEntryInfo get(Object key)
    {
        return(SellOrderPlanEntryInfo)getObject(key);
    }
    public void set(int index, SellOrderPlanEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(SellOrderPlanEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(SellOrderPlanEntryInfo item)
    {
        return super.indexOf(item);
    }
}