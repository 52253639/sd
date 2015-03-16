package com.kingdee.eas.fdc.market;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class MovementPlanE4Collection extends AbstractObjectCollection 
{
    public MovementPlanE4Collection()
    {
        super(MovementPlanE4Info.class);
    }
    public boolean add(MovementPlanE4Info item)
    {
        return addObject(item);
    }
    public boolean addCollection(MovementPlanE4Collection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(MovementPlanE4Info item)
    {
        return removeObject(item);
    }
    public MovementPlanE4Info get(int index)
    {
        return(MovementPlanE4Info)getObject(index);
    }
    public MovementPlanE4Info get(Object key)
    {
        return(MovementPlanE4Info)getObject(key);
    }
    public void set(int index, MovementPlanE4Info item)
    {
        setObject(index, item);
    }
    public boolean contains(MovementPlanE4Info item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(MovementPlanE4Info item)
    {
        return super.indexOf(item);
    }
}