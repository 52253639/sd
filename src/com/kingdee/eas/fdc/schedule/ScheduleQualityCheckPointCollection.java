package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ScheduleQualityCheckPointCollection extends AbstractObjectCollection 
{
    public ScheduleQualityCheckPointCollection()
    {
        super(ScheduleQualityCheckPointInfo.class);
    }
    public boolean add(ScheduleQualityCheckPointInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ScheduleQualityCheckPointCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ScheduleQualityCheckPointInfo item)
    {
        return removeObject(item);
    }
    public ScheduleQualityCheckPointInfo get(int index)
    {
        return(ScheduleQualityCheckPointInfo)getObject(index);
    }
    public ScheduleQualityCheckPointInfo get(Object key)
    {
        return(ScheduleQualityCheckPointInfo)getObject(key);
    }
    public void set(int index, ScheduleQualityCheckPointInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ScheduleQualityCheckPointInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ScheduleQualityCheckPointInfo item)
    {
        return super.indexOf(item);
    }
}