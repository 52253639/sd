package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ProjectMonthReportEntryCollection extends AbstractObjectCollection 
{
    public ProjectMonthReportEntryCollection()
    {
        super(ProjectMonthReportEntryInfo.class);
    }
    public boolean add(ProjectMonthReportEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ProjectMonthReportEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ProjectMonthReportEntryInfo item)
    {
        return removeObject(item);
    }
    public ProjectMonthReportEntryInfo get(int index)
    {
        return(ProjectMonthReportEntryInfo)getObject(index);
    }
    public ProjectMonthReportEntryInfo get(Object key)
    {
        return(ProjectMonthReportEntryInfo)getObject(key);
    }
    public void set(int index, ProjectMonthReportEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ProjectMonthReportEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ProjectMonthReportEntryInfo item)
    {
        return super.indexOf(item);
    }
}