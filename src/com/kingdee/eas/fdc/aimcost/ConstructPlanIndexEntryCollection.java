package com.kingdee.eas.fdc.aimcost;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ConstructPlanIndexEntryCollection extends AbstractObjectCollection 
{
    public ConstructPlanIndexEntryCollection()
    {
        super(ConstructPlanIndexEntryInfo.class);
    }
    public boolean add(ConstructPlanIndexEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ConstructPlanIndexEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ConstructPlanIndexEntryInfo item)
    {
        return removeObject(item);
    }
    public ConstructPlanIndexEntryInfo get(int index)
    {
        return(ConstructPlanIndexEntryInfo)getObject(index);
    }
    public ConstructPlanIndexEntryInfo get(Object key)
    {
        return(ConstructPlanIndexEntryInfo)getObject(key);
    }
    public void set(int index, ConstructPlanIndexEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ConstructPlanIndexEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ConstructPlanIndexEntryInfo item)
    {
        return super.indexOf(item);
    }
}