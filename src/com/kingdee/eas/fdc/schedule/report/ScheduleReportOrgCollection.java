package com.kingdee.eas.fdc.schedule.report;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ScheduleReportOrgCollection extends AbstractObjectCollection 
{
    public ScheduleReportOrgCollection()
    {
        super(ScheduleReportOrgInfo.class);
    }
    public boolean add(ScheduleReportOrgInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ScheduleReportOrgCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ScheduleReportOrgInfo item)
    {
        return removeObject(item);
    }
    public ScheduleReportOrgInfo get(int index)
    {
        return(ScheduleReportOrgInfo)getObject(index);
    }
    public ScheduleReportOrgInfo get(Object key)
    {
        return(ScheduleReportOrgInfo)getObject(key);
    }
    public void set(int index, ScheduleReportOrgInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ScheduleReportOrgInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ScheduleReportOrgInfo item)
    {
        return super.indexOf(item);
    }
}