package com.kingdee.eas.fdc.market;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class PlanEntrysSecCollection extends AbstractObjectCollection 
{
    public PlanEntrysSecCollection()
    {
        super(PlanEntrysSecInfo.class);
    }
    public boolean add(PlanEntrysSecInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(PlanEntrysSecCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(PlanEntrysSecInfo item)
    {
        return removeObject(item);
    }
    public PlanEntrysSecInfo get(int index)
    {
        return(PlanEntrysSecInfo)getObject(index);
    }
    public PlanEntrysSecInfo get(Object key)
    {
        return(PlanEntrysSecInfo)getObject(key);
    }
    public void set(int index, PlanEntrysSecInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(PlanEntrysSecInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(PlanEntrysSecInfo item)
    {
        return super.indexOf(item);
    }
}