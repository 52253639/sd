package com.kingdee.eas.fdc.market;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class MovementPlanE2Collection extends AbstractObjectCollection 
{
    public MovementPlanE2Collection()
    {
        super(MovementPlanE2Info.class);
    }
    public boolean add(MovementPlanE2Info item)
    {
        return addObject(item);
    }
    public boolean addCollection(MovementPlanE2Collection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(MovementPlanE2Info item)
    {
        return removeObject(item);
    }
    public MovementPlanE2Info get(int index)
    {
        return(MovementPlanE2Info)getObject(index);
    }
    public MovementPlanE2Info get(Object key)
    {
        return(MovementPlanE2Info)getObject(key);
    }
    public void set(int index, MovementPlanE2Info item)
    {
        setObject(index, item);
    }
    public boolean contains(MovementPlanE2Info item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(MovementPlanE2Info item)
    {
        return super.indexOf(item);
    }
}