package com.kingdee.eas.fdc.market;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class MovementPlanE5Collection extends AbstractObjectCollection 
{
    public MovementPlanE5Collection()
    {
        super(MovementPlanE5Info.class);
    }
    public boolean add(MovementPlanE5Info item)
    {
        return addObject(item);
    }
    public boolean addCollection(MovementPlanE5Collection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(MovementPlanE5Info item)
    {
        return removeObject(item);
    }
    public MovementPlanE5Info get(int index)
    {
        return(MovementPlanE5Info)getObject(index);
    }
    public MovementPlanE5Info get(Object key)
    {
        return(MovementPlanE5Info)getObject(key);
    }
    public void set(int index, MovementPlanE5Info item)
    {
        setObject(index, item);
    }
    public boolean contains(MovementPlanE5Info item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(MovementPlanE5Info item)
    {
        return super.indexOf(item);
    }
}