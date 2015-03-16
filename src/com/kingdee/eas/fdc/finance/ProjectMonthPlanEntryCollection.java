package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ProjectMonthPlanEntryCollection extends AbstractObjectCollection 
{
    public ProjectMonthPlanEntryCollection()
    {
        super(ProjectMonthPlanEntryInfo.class);
    }
    public boolean add(ProjectMonthPlanEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ProjectMonthPlanEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ProjectMonthPlanEntryInfo item)
    {
        return removeObject(item);
    }
    public ProjectMonthPlanEntryInfo get(int index)
    {
        return(ProjectMonthPlanEntryInfo)getObject(index);
    }
    public ProjectMonthPlanEntryInfo get(Object key)
    {
        return(ProjectMonthPlanEntryInfo)getObject(key);
    }
    public void set(int index, ProjectMonthPlanEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ProjectMonthPlanEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ProjectMonthPlanEntryInfo item)
    {
        return super.indexOf(item);
    }
}