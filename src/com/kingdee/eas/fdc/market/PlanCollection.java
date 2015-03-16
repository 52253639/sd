package com.kingdee.eas.fdc.market;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class PlanCollection extends AbstractObjectCollection 
{
    public PlanCollection()
    {
        super(PlanInfo.class);
    }
    public boolean add(PlanInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(PlanCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(PlanInfo item)
    {
        return removeObject(item);
    }
    public PlanInfo get(int index)
    {
        return(PlanInfo)getObject(index);
    }
    public PlanInfo get(Object key)
    {
        return(PlanInfo)getObject(key);
    }
    public void set(int index, PlanInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(PlanInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(PlanInfo item)
    {
        return super.indexOf(item);
    }
}