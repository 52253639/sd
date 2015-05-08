package com.kingdee.eas.fdc.schedule.framework;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ScheduleResourceBaseCollection extends AbstractObjectCollection 
{
    public ScheduleResourceBaseCollection()
    {
        super(ScheduleResourceBaseInfo.class);
    }
    public boolean add(ScheduleResourceBaseInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ScheduleResourceBaseCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ScheduleResourceBaseInfo item)
    {
        return removeObject(item);
    }
    public ScheduleResourceBaseInfo get(int index)
    {
        return(ScheduleResourceBaseInfo)getObject(index);
    }
    public ScheduleResourceBaseInfo get(Object key)
    {
        return(ScheduleResourceBaseInfo)getObject(key);
    }
    public void set(int index, ScheduleResourceBaseInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ScheduleResourceBaseInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ScheduleResourceBaseInfo item)
    {
        return super.indexOf(item);
    }
}