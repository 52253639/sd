package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ProjectWeekReportCollection extends AbstractObjectCollection 
{
    public ProjectWeekReportCollection()
    {
        super(ProjectWeekReportInfo.class);
    }
    public boolean add(ProjectWeekReportInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ProjectWeekReportCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ProjectWeekReportInfo item)
    {
        return removeObject(item);
    }
    public ProjectWeekReportInfo get(int index)
    {
        return(ProjectWeekReportInfo)getObject(index);
    }
    public ProjectWeekReportInfo get(Object key)
    {
        return(ProjectWeekReportInfo)getObject(key);
    }
    public void set(int index, ProjectWeekReportInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ProjectWeekReportInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ProjectWeekReportInfo item)
    {
        return super.indexOf(item);
    }
}