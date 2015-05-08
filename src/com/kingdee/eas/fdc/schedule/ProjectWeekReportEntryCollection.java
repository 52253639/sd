package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ProjectWeekReportEntryCollection extends AbstractObjectCollection 
{
    public ProjectWeekReportEntryCollection()
    {
        super(ProjectWeekReportEntryInfo.class);
    }
    public boolean add(ProjectWeekReportEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ProjectWeekReportEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ProjectWeekReportEntryInfo item)
    {
        return removeObject(item);
    }
    public ProjectWeekReportEntryInfo get(int index)
    {
        return(ProjectWeekReportEntryInfo)getObject(index);
    }
    public ProjectWeekReportEntryInfo get(Object key)
    {
        return(ProjectWeekReportEntryInfo)getObject(key);
    }
    public void set(int index, ProjectWeekReportEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ProjectWeekReportEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ProjectWeekReportEntryInfo item)
    {
        return super.indexOf(item);
    }
}