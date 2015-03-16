package com.kingdee.eas.fdc.market;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class MovementPlanE6Collection extends AbstractObjectCollection 
{
    public MovementPlanE6Collection()
    {
        super(MovementPlanE6Info.class);
    }
    public boolean add(MovementPlanE6Info item)
    {
        return addObject(item);
    }
    public boolean addCollection(MovementPlanE6Collection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(MovementPlanE6Info item)
    {
        return removeObject(item);
    }
    public MovementPlanE6Info get(int index)
    {
        return(MovementPlanE6Info)getObject(index);
    }
    public MovementPlanE6Info get(Object key)
    {
        return(MovementPlanE6Info)getObject(key);
    }
    public void set(int index, MovementPlanE6Info item)
    {
        setObject(index, item);
    }
    public boolean contains(MovementPlanE6Info item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(MovementPlanE6Info item)
    {
        return super.indexOf(item);
    }
}