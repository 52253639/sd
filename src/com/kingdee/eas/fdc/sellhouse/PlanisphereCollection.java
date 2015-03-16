package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class PlanisphereCollection extends AbstractObjectCollection 
{
    public PlanisphereCollection()
    {
        super(PlanisphereInfo.class);
    }
    public boolean add(PlanisphereInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(PlanisphereCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(PlanisphereInfo item)
    {
        return removeObject(item);
    }
    public PlanisphereInfo get(int index)
    {
        return(PlanisphereInfo)getObject(index);
    }
    public PlanisphereInfo get(Object key)
    {
        return(PlanisphereInfo)getObject(key);
    }
    public void set(int index, PlanisphereInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(PlanisphereInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(PlanisphereInfo item)
    {
        return super.indexOf(item);
    }
}