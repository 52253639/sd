package com.kingdee.eas.fdc.aimcost;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class DynCostSnapShotCollection extends AbstractObjectCollection 
{
    public DynCostSnapShotCollection()
    {
        super(DynCostSnapShotInfo.class);
    }
    public boolean add(DynCostSnapShotInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(DynCostSnapShotCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(DynCostSnapShotInfo item)
    {
        return removeObject(item);
    }
    public DynCostSnapShotInfo get(int index)
    {
        return(DynCostSnapShotInfo)getObject(index);
    }
    public DynCostSnapShotInfo get(Object key)
    {
        return(DynCostSnapShotInfo)getObject(key);
    }
    public void set(int index, DynCostSnapShotInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(DynCostSnapShotInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(DynCostSnapShotInfo item)
    {
        return super.indexOf(item);
    }
}