package com.kingdee.eas.fdc.market;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class MovementPlanEntryCollection extends AbstractObjectCollection 
{
    public MovementPlanEntryCollection()
    {
        super(MovementPlanEntryInfo.class);
    }
    public boolean add(MovementPlanEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(MovementPlanEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(MovementPlanEntryInfo item)
    {
        return removeObject(item);
    }
    public MovementPlanEntryInfo get(int index)
    {
        return(MovementPlanEntryInfo)getObject(index);
    }
    public MovementPlanEntryInfo get(Object key)
    {
        return(MovementPlanEntryInfo)getObject(key);
    }
    public void set(int index, MovementPlanEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(MovementPlanEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(MovementPlanEntryInfo item)
    {
        return super.indexOf(item);
    }
}