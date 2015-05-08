package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ScheduleVerManagerCollection extends AbstractObjectCollection 
{
    public ScheduleVerManagerCollection()
    {
        super(ScheduleVerManagerInfo.class);
    }
    public boolean add(ScheduleVerManagerInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ScheduleVerManagerCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ScheduleVerManagerInfo item)
    {
        return removeObject(item);
    }
    public ScheduleVerManagerInfo get(int index)
    {
        return(ScheduleVerManagerInfo)getObject(index);
    }
    public ScheduleVerManagerInfo get(Object key)
    {
        return(ScheduleVerManagerInfo)getObject(key);
    }
    public void set(int index, ScheduleVerManagerInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ScheduleVerManagerInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ScheduleVerManagerInfo item)
    {
        return super.indexOf(item);
    }
}