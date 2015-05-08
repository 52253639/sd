package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ScheduleTaskBizTypeCollection extends AbstractObjectCollection 
{
    public ScheduleTaskBizTypeCollection()
    {
        super(ScheduleTaskBizTypeInfo.class);
    }
    public boolean add(ScheduleTaskBizTypeInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ScheduleTaskBizTypeCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ScheduleTaskBizTypeInfo item)
    {
        return removeObject(item);
    }
    public ScheduleTaskBizTypeInfo get(int index)
    {
        return(ScheduleTaskBizTypeInfo)getObject(index);
    }
    public ScheduleTaskBizTypeInfo get(Object key)
    {
        return(ScheduleTaskBizTypeInfo)getObject(key);
    }
    public void set(int index, ScheduleTaskBizTypeInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ScheduleTaskBizTypeInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ScheduleTaskBizTypeInfo item)
    {
        return super.indexOf(item);
    }
}