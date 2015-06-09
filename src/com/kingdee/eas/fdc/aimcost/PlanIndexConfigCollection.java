package com.kingdee.eas.fdc.aimcost;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class PlanIndexConfigCollection extends AbstractObjectCollection 
{
    public PlanIndexConfigCollection()
    {
        super(PlanIndexConfigInfo.class);
    }
    public boolean add(PlanIndexConfigInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(PlanIndexConfigCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(PlanIndexConfigInfo item)
    {
        return removeObject(item);
    }
    public PlanIndexConfigInfo get(int index)
    {
        return(PlanIndexConfigInfo)getObject(index);
    }
    public PlanIndexConfigInfo get(Object key)
    {
        return(PlanIndexConfigInfo)getObject(key);
    }
    public void set(int index, PlanIndexConfigInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(PlanIndexConfigInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(PlanIndexConfigInfo item)
    {
        return super.indexOf(item);
    }
}