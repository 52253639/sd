package com.kingdee.eas.fdc.market;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class MovementTypeCollection extends AbstractObjectCollection 
{
    public MovementTypeCollection()
    {
        super(MovementTypeInfo.class);
    }
    public boolean add(MovementTypeInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(MovementTypeCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(MovementTypeInfo item)
    {
        return removeObject(item);
    }
    public MovementTypeInfo get(int index)
    {
        return(MovementTypeInfo)getObject(index);
    }
    public MovementTypeInfo get(Object key)
    {
        return(MovementTypeInfo)getObject(key);
    }
    public void set(int index, MovementTypeInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(MovementTypeInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(MovementTypeInfo item)
    {
        return super.indexOf(item);
    }
}