package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ScheduleBizTypeCollection extends AbstractObjectCollection 
{
    public ScheduleBizTypeCollection()
    {
        super(ScheduleBizTypeInfo.class);
    }
    public boolean add(ScheduleBizTypeInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ScheduleBizTypeCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ScheduleBizTypeInfo item)
    {
        return removeObject(item);
    }
    public ScheduleBizTypeInfo get(int index)
    {
        return(ScheduleBizTypeInfo)getObject(index);
    }
    public ScheduleBizTypeInfo get(Object key)
    {
        return(ScheduleBizTypeInfo)getObject(key);
    }
    public void set(int index, ScheduleBizTypeInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ScheduleBizTypeInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ScheduleBizTypeInfo item)
    {
        return super.indexOf(item);
    }
}