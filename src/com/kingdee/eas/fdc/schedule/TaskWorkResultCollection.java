package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class TaskWorkResultCollection extends AbstractObjectCollection 
{
    public TaskWorkResultCollection()
    {
        super(TaskWorkResultInfo.class);
    }
    public boolean add(TaskWorkResultInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(TaskWorkResultCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(TaskWorkResultInfo item)
    {
        return removeObject(item);
    }
    public TaskWorkResultInfo get(int index)
    {
        return(TaskWorkResultInfo)getObject(index);
    }
    public TaskWorkResultInfo get(Object key)
    {
        return(TaskWorkResultInfo)getObject(key);
    }
    public void set(int index, TaskWorkResultInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(TaskWorkResultInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(TaskWorkResultInfo item)
    {
        return super.indexOf(item);
    }
}