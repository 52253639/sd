package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ScheduleTaskProgressReportCollection extends AbstractObjectCollection 
{
    public ScheduleTaskProgressReportCollection()
    {
        super(ScheduleTaskProgressReportInfo.class);
    }
    public boolean add(ScheduleTaskProgressReportInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ScheduleTaskProgressReportCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ScheduleTaskProgressReportInfo item)
    {
        return removeObject(item);
    }
    public ScheduleTaskProgressReportInfo get(int index)
    {
        return(ScheduleTaskProgressReportInfo)getObject(index);
    }
    public ScheduleTaskProgressReportInfo get(Object key)
    {
        return(ScheduleTaskProgressReportInfo)getObject(key);
    }
    public void set(int index, ScheduleTaskProgressReportInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ScheduleTaskProgressReportInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ScheduleTaskProgressReportInfo item)
    {
        return super.indexOf(item);
    }
}