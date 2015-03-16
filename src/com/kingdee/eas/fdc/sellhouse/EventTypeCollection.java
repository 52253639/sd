package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class EventTypeCollection extends AbstractObjectCollection 
{
    public EventTypeCollection()
    {
        super(EventTypeInfo.class);
    }
    public boolean add(EventTypeInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(EventTypeCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(EventTypeInfo item)
    {
        return removeObject(item);
    }
    public EventTypeInfo get(int index)
    {
        return(EventTypeInfo)getObject(index);
    }
    public EventTypeInfo get(Object key)
    {
        return(EventTypeInfo)getObject(key);
    }
    public void set(int index, EventTypeInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(EventTypeInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(EventTypeInfo item)
    {
        return super.indexOf(item);
    }
}