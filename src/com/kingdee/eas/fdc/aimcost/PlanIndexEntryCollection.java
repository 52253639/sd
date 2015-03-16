package com.kingdee.eas.fdc.aimcost;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class PlanIndexEntryCollection extends AbstractObjectCollection 
{
    public PlanIndexEntryCollection()
    {
        super(PlanIndexEntryInfo.class);
    }
    public boolean add(PlanIndexEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(PlanIndexEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(PlanIndexEntryInfo item)
    {
        return removeObject(item);
    }
    public PlanIndexEntryInfo get(int index)
    {
        return(PlanIndexEntryInfo)getObject(index);
    }
    public PlanIndexEntryInfo get(Object key)
    {
        return(PlanIndexEntryInfo)getObject(key);
    }
    public void set(int index, PlanIndexEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(PlanIndexEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(PlanIndexEntryInfo item)
    {
        return super.indexOf(item);
    }
}