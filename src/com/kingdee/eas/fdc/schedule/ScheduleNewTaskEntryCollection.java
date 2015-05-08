package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ScheduleNewTaskEntryCollection extends AbstractObjectCollection 
{
    public ScheduleNewTaskEntryCollection()
    {
        super(ScheduleNewTaskEntryInfo.class);
    }
    public boolean add(ScheduleNewTaskEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ScheduleNewTaskEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ScheduleNewTaskEntryInfo item)
    {
        return removeObject(item);
    }
    public ScheduleNewTaskEntryInfo get(int index)
    {
        return(ScheduleNewTaskEntryInfo)getObject(index);
    }
    public ScheduleNewTaskEntryInfo get(Object key)
    {
        return(ScheduleNewTaskEntryInfo)getObject(key);
    }
    public void set(int index, ScheduleNewTaskEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ScheduleNewTaskEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ScheduleNewTaskEntryInfo item)
    {
        return super.indexOf(item);
    }
}