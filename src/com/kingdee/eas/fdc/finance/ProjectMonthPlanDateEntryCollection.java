package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ProjectMonthPlanDateEntryCollection extends AbstractObjectCollection 
{
    public ProjectMonthPlanDateEntryCollection()
    {
        super(ProjectMonthPlanDateEntryInfo.class);
    }
    public boolean add(ProjectMonthPlanDateEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ProjectMonthPlanDateEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ProjectMonthPlanDateEntryInfo item)
    {
        return removeObject(item);
    }
    public ProjectMonthPlanDateEntryInfo get(int index)
    {
        return(ProjectMonthPlanDateEntryInfo)getObject(index);
    }
    public ProjectMonthPlanDateEntryInfo get(Object key)
    {
        return(ProjectMonthPlanDateEntryInfo)getObject(key);
    }
    public void set(int index, ProjectMonthPlanDateEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ProjectMonthPlanDateEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ProjectMonthPlanDateEntryInfo item)
    {
        return super.indexOf(item);
    }
}