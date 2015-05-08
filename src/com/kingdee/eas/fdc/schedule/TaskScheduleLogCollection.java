package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class TaskScheduleLogCollection extends AbstractObjectCollection 
{
    public TaskScheduleLogCollection()
    {
        super(TaskScheduleLogInfo.class);
    }
    public boolean add(TaskScheduleLogInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(TaskScheduleLogCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(TaskScheduleLogInfo item)
    {
        return removeObject(item);
    }
    public TaskScheduleLogInfo get(int index)
    {
        return(TaskScheduleLogInfo)getObject(index);
    }
    public TaskScheduleLogInfo get(Object key)
    {
        return(TaskScheduleLogInfo)getObject(key);
    }
    public void set(int index, TaskScheduleLogInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(TaskScheduleLogInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(TaskScheduleLogInfo item)
    {
        return super.indexOf(item);
    }
}