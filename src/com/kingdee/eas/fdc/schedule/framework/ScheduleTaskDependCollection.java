package com.kingdee.eas.fdc.schedule.framework;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ScheduleTaskDependCollection extends AbstractObjectCollection 
{
    public ScheduleTaskDependCollection()
    {
        super(ScheduleTaskDependInfo.class);
    }
    public boolean add(ScheduleTaskDependInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ScheduleTaskDependCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ScheduleTaskDependInfo item)
    {
        return removeObject(item);
    }
    public ScheduleTaskDependInfo get(int index)
    {
        return(ScheduleTaskDependInfo)getObject(index);
    }
    public ScheduleTaskDependInfo get(Object key)
    {
        return(ScheduleTaskDependInfo)getObject(key);
    }
    public void set(int index, ScheduleTaskDependInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ScheduleTaskDependInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ScheduleTaskDependInfo item)
    {
        return super.indexOf(item);
    }
}