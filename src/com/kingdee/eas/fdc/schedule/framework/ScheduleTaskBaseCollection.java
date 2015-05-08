package com.kingdee.eas.fdc.schedule.framework;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ScheduleTaskBaseCollection extends AbstractObjectCollection 
{
    public ScheduleTaskBaseCollection()
    {
        super(ScheduleTaskBaseInfo.class);
    }
    public boolean add(ScheduleTaskBaseInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ScheduleTaskBaseCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ScheduleTaskBaseInfo item)
    {
        return removeObject(item);
    }
    public ScheduleTaskBaseInfo get(int index)
    {
        return(ScheduleTaskBaseInfo)getObject(index);
    }
    public ScheduleTaskBaseInfo get(Object key)
    {
        return(ScheduleTaskBaseInfo)getObject(key);
    }
    public void set(int index, ScheduleTaskBaseInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ScheduleTaskBaseInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ScheduleTaskBaseInfo item)
    {
        return super.indexOf(item);
    }
}