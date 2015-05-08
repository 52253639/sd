package com.kingdee.eas.fdc.schedule.framework;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ScheduleBaseCollection extends AbstractObjectCollection 
{
    public ScheduleBaseCollection()
    {
        super(ScheduleBaseInfo.class);
    }
    public boolean add(ScheduleBaseInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ScheduleBaseCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ScheduleBaseInfo item)
    {
        return removeObject(item);
    }
    public ScheduleBaseInfo get(int index)
    {
        return(ScheduleBaseInfo)getObject(index);
    }
    public ScheduleBaseInfo get(Object key)
    {
        return(ScheduleBaseInfo)getObject(key);
    }
    public void set(int index, ScheduleBaseInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ScheduleBaseInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ScheduleBaseInfo item)
    {
        return super.indexOf(item);
    }
}