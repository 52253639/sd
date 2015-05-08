package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ScheduleAdjustTaskEntryCollection extends AbstractObjectCollection 
{
    public ScheduleAdjustTaskEntryCollection()
    {
        super(ScheduleAdjustTaskEntryInfo.class);
    }
    public boolean add(ScheduleAdjustTaskEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ScheduleAdjustTaskEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ScheduleAdjustTaskEntryInfo item)
    {
        return removeObject(item);
    }
    public ScheduleAdjustTaskEntryInfo get(int index)
    {
        return(ScheduleAdjustTaskEntryInfo)getObject(index);
    }
    public ScheduleAdjustTaskEntryInfo get(Object key)
    {
        return(ScheduleAdjustTaskEntryInfo)getObject(key);
    }
    public void set(int index, ScheduleAdjustTaskEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ScheduleAdjustTaskEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ScheduleAdjustTaskEntryInfo item)
    {
        return super.indexOf(item);
    }
}