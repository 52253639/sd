package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ScheduleAdjustTaskDependCollection extends AbstractObjectCollection 
{
    public ScheduleAdjustTaskDependCollection()
    {
        super(ScheduleAdjustTaskDependInfo.class);
    }
    public boolean add(ScheduleAdjustTaskDependInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ScheduleAdjustTaskDependCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ScheduleAdjustTaskDependInfo item)
    {
        return removeObject(item);
    }
    public ScheduleAdjustTaskDependInfo get(int index)
    {
        return(ScheduleAdjustTaskDependInfo)getObject(index);
    }
    public ScheduleAdjustTaskDependInfo get(Object key)
    {
        return(ScheduleAdjustTaskDependInfo)getObject(key);
    }
    public void set(int index, ScheduleAdjustTaskDependInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ScheduleAdjustTaskDependInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ScheduleAdjustTaskDependInfo item)
    {
        return super.indexOf(item);
    }
}