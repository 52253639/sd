package com.kingdee.eas.fdc.schedule.framework;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ScheduleCalendarCollection extends AbstractObjectCollection 
{
    public ScheduleCalendarCollection()
    {
        super(ScheduleCalendarInfo.class);
    }
    public boolean add(ScheduleCalendarInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ScheduleCalendarCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ScheduleCalendarInfo item)
    {
        return removeObject(item);
    }
    public ScheduleCalendarInfo get(int index)
    {
        return(ScheduleCalendarInfo)getObject(index);
    }
    public ScheduleCalendarInfo get(Object key)
    {
        return(ScheduleCalendarInfo)getObject(key);
    }
    public void set(int index, ScheduleCalendarInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ScheduleCalendarInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ScheduleCalendarInfo item)
    {
        return super.indexOf(item);
    }
}