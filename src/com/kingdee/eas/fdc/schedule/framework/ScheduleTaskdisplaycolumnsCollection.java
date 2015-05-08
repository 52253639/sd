package com.kingdee.eas.fdc.schedule.framework;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ScheduleTaskdisplaycolumnsCollection extends AbstractObjectCollection 
{
    public ScheduleTaskdisplaycolumnsCollection()
    {
        super(ScheduleTaskdisplaycolumnsInfo.class);
    }
    public boolean add(ScheduleTaskdisplaycolumnsInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ScheduleTaskdisplaycolumnsCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ScheduleTaskdisplaycolumnsInfo item)
    {
        return removeObject(item);
    }
    public ScheduleTaskdisplaycolumnsInfo get(int index)
    {
        return(ScheduleTaskdisplaycolumnsInfo)getObject(index);
    }
    public ScheduleTaskdisplaycolumnsInfo get(Object key)
    {
        return(ScheduleTaskdisplaycolumnsInfo)getObject(key);
    }
    public void set(int index, ScheduleTaskdisplaycolumnsInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ScheduleTaskdisplaycolumnsInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ScheduleTaskdisplaycolumnsInfo item)
    {
        return super.indexOf(item);
    }
}