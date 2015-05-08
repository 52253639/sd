package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class TaskTypeCollection extends AbstractObjectCollection 
{
    public TaskTypeCollection()
    {
        super(TaskTypeInfo.class);
    }
    public boolean add(TaskTypeInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(TaskTypeCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(TaskTypeInfo item)
    {
        return removeObject(item);
    }
    public TaskTypeInfo get(int index)
    {
        return(TaskTypeInfo)getObject(index);
    }
    public TaskTypeInfo get(Object key)
    {
        return(TaskTypeInfo)getObject(key);
    }
    public void set(int index, TaskTypeInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(TaskTypeInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(TaskTypeInfo item)
    {
        return super.indexOf(item);
    }
}