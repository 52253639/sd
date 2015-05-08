package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class TaskWorkloadLogCollection extends AbstractObjectCollection 
{
    public TaskWorkloadLogCollection()
    {
        super(TaskWorkloadLogInfo.class);
    }
    public boolean add(TaskWorkloadLogInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(TaskWorkloadLogCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(TaskWorkloadLogInfo item)
    {
        return removeObject(item);
    }
    public TaskWorkloadLogInfo get(int index)
    {
        return(TaskWorkloadLogInfo)getObject(index);
    }
    public TaskWorkloadLogInfo get(Object key)
    {
        return(TaskWorkloadLogInfo)getObject(key);
    }
    public void set(int index, TaskWorkloadLogInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(TaskWorkloadLogInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(TaskWorkloadLogInfo item)
    {
        return super.indexOf(item);
    }
}