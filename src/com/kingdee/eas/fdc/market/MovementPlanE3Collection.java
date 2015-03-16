package com.kingdee.eas.fdc.market;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class MovementPlanE3Collection extends AbstractObjectCollection 
{
    public MovementPlanE3Collection()
    {
        super(MovementPlanE3Info.class);
    }
    public boolean add(MovementPlanE3Info item)
    {
        return addObject(item);
    }
    public boolean addCollection(MovementPlanE3Collection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(MovementPlanE3Info item)
    {
        return removeObject(item);
    }
    public MovementPlanE3Info get(int index)
    {
        return(MovementPlanE3Info)getObject(index);
    }
    public MovementPlanE3Info get(Object key)
    {
        return(MovementPlanE3Info)getObject(key);
    }
    public void set(int index, MovementPlanE3Info item)
    {
        setObject(index, item);
    }
    public boolean contains(MovementPlanE3Info item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(MovementPlanE3Info item)
    {
        return super.indexOf(item);
    }
}