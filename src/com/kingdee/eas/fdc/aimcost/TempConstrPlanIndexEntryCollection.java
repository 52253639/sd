package com.kingdee.eas.fdc.aimcost;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class TempConstrPlanIndexEntryCollection extends AbstractObjectCollection 
{
    public TempConstrPlanIndexEntryCollection()
    {
        super(TempConstrPlanIndexEntryInfo.class);
    }
    public boolean add(TempConstrPlanIndexEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(TempConstrPlanIndexEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(TempConstrPlanIndexEntryInfo item)
    {
        return removeObject(item);
    }
    public TempConstrPlanIndexEntryInfo get(int index)
    {
        return(TempConstrPlanIndexEntryInfo)getObject(index);
    }
    public TempConstrPlanIndexEntryInfo get(Object key)
    {
        return(TempConstrPlanIndexEntryInfo)getObject(key);
    }
    public void set(int index, TempConstrPlanIndexEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(TempConstrPlanIndexEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(TempConstrPlanIndexEntryInfo item)
    {
        return super.indexOf(item);
    }
}