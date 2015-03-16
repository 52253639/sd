package com.kingdee.eas.fdc.market;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class PlanTypeCollection extends AbstractObjectCollection 
{
    public PlanTypeCollection()
    {
        super(PlanTypeInfo.class);
    }
    public boolean add(PlanTypeInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(PlanTypeCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(PlanTypeInfo item)
    {
        return removeObject(item);
    }
    public PlanTypeInfo get(int index)
    {
        return(PlanTypeInfo)getObject(index);
    }
    public PlanTypeInfo get(Object key)
    {
        return(PlanTypeInfo)getObject(key);
    }
    public void set(int index, PlanTypeInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(PlanTypeInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(PlanTypeInfo item)
    {
        return super.indexOf(item);
    }
}