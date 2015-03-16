package com.kingdee.eas.fdc.aimcost;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class DynCostSnapShotProTypEntryCollection extends AbstractObjectCollection 
{
    public DynCostSnapShotProTypEntryCollection()
    {
        super(DynCostSnapShotProTypEntryInfo.class);
    }
    public boolean add(DynCostSnapShotProTypEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(DynCostSnapShotProTypEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(DynCostSnapShotProTypEntryInfo item)
    {
        return removeObject(item);
    }
    public DynCostSnapShotProTypEntryInfo get(int index)
    {
        return(DynCostSnapShotProTypEntryInfo)getObject(index);
    }
    public DynCostSnapShotProTypEntryInfo get(Object key)
    {
        return(DynCostSnapShotProTypEntryInfo)getObject(key);
    }
    public void set(int index, DynCostSnapShotProTypEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(DynCostSnapShotProTypEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(DynCostSnapShotProTypEntryInfo item)
    {
        return super.indexOf(item);
    }
}