package com.kingdee.eas.fdc.aimcost;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class IntendingCostEntryCollection extends AbstractObjectCollection 
{
    public IntendingCostEntryCollection()
    {
        super(IntendingCostEntryInfo.class);
    }
    public boolean add(IntendingCostEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(IntendingCostEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(IntendingCostEntryInfo item)
    {
        return removeObject(item);
    }
    public IntendingCostEntryInfo get(int index)
    {
        return(IntendingCostEntryInfo)getObject(index);
    }
    public IntendingCostEntryInfo get(Object key)
    {
        return(IntendingCostEntryInfo)getObject(key);
    }
    public void set(int index, IntendingCostEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(IntendingCostEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(IntendingCostEntryInfo item)
    {
        return super.indexOf(item);
    }
}