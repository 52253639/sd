package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ScheduleAdjustPrefixTaskCollection extends AbstractObjectCollection 
{
    public ScheduleAdjustPrefixTaskCollection()
    {
        super(ScheduleAdjustPrefixTaskInfo.class);
    }
    public boolean add(ScheduleAdjustPrefixTaskInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ScheduleAdjustPrefixTaskCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ScheduleAdjustPrefixTaskInfo item)
    {
        return removeObject(item);
    }
    public ScheduleAdjustPrefixTaskInfo get(int index)
    {
        return(ScheduleAdjustPrefixTaskInfo)getObject(index);
    }
    public ScheduleAdjustPrefixTaskInfo get(Object key)
    {
        return(ScheduleAdjustPrefixTaskInfo)getObject(key);
    }
    public void set(int index, ScheduleAdjustPrefixTaskInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ScheduleAdjustPrefixTaskInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ScheduleAdjustPrefixTaskInfo item)
    {
        return super.indexOf(item);
    }
}