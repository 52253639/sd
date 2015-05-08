package com.kingdee.eas.fdc.schedule.framework;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ScheduleTaskPropertyCollection extends AbstractObjectCollection 
{
    public ScheduleTaskPropertyCollection()
    {
        super(ScheduleTaskPropertyInfo.class);
    }
    public boolean add(ScheduleTaskPropertyInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ScheduleTaskPropertyCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ScheduleTaskPropertyInfo item)
    {
        return removeObject(item);
    }
    public ScheduleTaskPropertyInfo get(int index)
    {
        return(ScheduleTaskPropertyInfo)getObject(index);
    }
    public ScheduleTaskPropertyInfo get(Object key)
    {
        return(ScheduleTaskPropertyInfo)getObject(key);
    }
    public void set(int index, ScheduleTaskPropertyInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ScheduleTaskPropertyInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ScheduleTaskPropertyInfo item)
    {
        return super.indexOf(item);
    }
}