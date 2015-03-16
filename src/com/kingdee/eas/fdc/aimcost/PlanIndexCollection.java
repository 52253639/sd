package com.kingdee.eas.fdc.aimcost;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class PlanIndexCollection extends AbstractObjectCollection 
{
    public PlanIndexCollection()
    {
        super(PlanIndexInfo.class);
    }
    public boolean add(PlanIndexInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(PlanIndexCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(PlanIndexInfo item)
    {
        return removeObject(item);
    }
    public PlanIndexInfo get(int index)
    {
        return(PlanIndexInfo)getObject(index);
    }
    public PlanIndexInfo get(Object key)
    {
        return(PlanIndexInfo)getObject(key);
    }
    public void set(int index, PlanIndexInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(PlanIndexInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(PlanIndexInfo item)
    {
        return super.indexOf(item);
    }
}