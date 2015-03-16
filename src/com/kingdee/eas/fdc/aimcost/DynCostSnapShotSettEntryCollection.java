package com.kingdee.eas.fdc.aimcost;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class DynCostSnapShotSettEntryCollection extends AbstractObjectCollection 
{
    public DynCostSnapShotSettEntryCollection()
    {
        super(DynCostSnapShotSettEntryInfo.class);
    }
    public boolean add(DynCostSnapShotSettEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(DynCostSnapShotSettEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(DynCostSnapShotSettEntryInfo item)
    {
        return removeObject(item);
    }
    public DynCostSnapShotSettEntryInfo get(int index)
    {
        return(DynCostSnapShotSettEntryInfo)getObject(index);
    }
    public DynCostSnapShotSettEntryInfo get(Object key)
    {
        return(DynCostSnapShotSettEntryInfo)getObject(key);
    }
    public void set(int index, DynCostSnapShotSettEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(DynCostSnapShotSettEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(DynCostSnapShotSettEntryInfo item)
    {
        return super.indexOf(item);
    }
}