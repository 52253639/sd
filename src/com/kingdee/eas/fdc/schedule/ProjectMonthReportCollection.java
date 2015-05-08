package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ProjectMonthReportCollection extends AbstractObjectCollection 
{
    public ProjectMonthReportCollection()
    {
        super(ProjectMonthReportInfo.class);
    }
    public boolean add(ProjectMonthReportInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ProjectMonthReportCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ProjectMonthReportInfo item)
    {
        return removeObject(item);
    }
    public ProjectMonthReportInfo get(int index)
    {
        return(ProjectMonthReportInfo)getObject(index);
    }
    public ProjectMonthReportInfo get(Object key)
    {
        return(ProjectMonthReportInfo)getObject(key);
    }
    public void set(int index, ProjectMonthReportInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ProjectMonthReportInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ProjectMonthReportInfo item)
    {
        return super.indexOf(item);
    }
}