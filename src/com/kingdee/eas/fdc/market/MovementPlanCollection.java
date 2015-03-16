package com.kingdee.eas.fdc.market;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class MovementPlanCollection extends AbstractObjectCollection 
{
    public MovementPlanCollection()
    {
        super(MovementPlanInfo.class);
    }
    public boolean add(MovementPlanInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(MovementPlanCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(MovementPlanInfo item)
    {
        return removeObject(item);
    }
    public MovementPlanInfo get(int index)
    {
        return(MovementPlanInfo)getObject(index);
    }
    public MovementPlanInfo get(Object key)
    {
        return(MovementPlanInfo)getObject(key);
    }
    public void set(int index, MovementPlanInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(MovementPlanInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(MovementPlanInfo item)
    {
        return super.indexOf(item);
    }
}