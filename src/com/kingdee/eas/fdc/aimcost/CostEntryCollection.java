package com.kingdee.eas.fdc.aimcost;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class CostEntryCollection extends AbstractObjectCollection 
{
    public CostEntryCollection()
    {
        super(CostEntryInfo.class);
    }
    public boolean add(CostEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(CostEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(CostEntryInfo item)
    {
        return removeObject(item);
    }
    public CostEntryInfo get(int index)
    {
        return(CostEntryInfo)getObject(index);
    }
    public CostEntryInfo get(Object key)
    {
        return(CostEntryInfo)getObject(key);
    }
    public void set(int index, CostEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(CostEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(CostEntryInfo item)
    {
        return super.indexOf(item);
    }
}