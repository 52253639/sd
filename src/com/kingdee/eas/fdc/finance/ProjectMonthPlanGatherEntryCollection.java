package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ProjectMonthPlanGatherEntryCollection extends AbstractObjectCollection 
{
    public ProjectMonthPlanGatherEntryCollection()
    {
        super(ProjectMonthPlanGatherEntryInfo.class);
    }
    public boolean add(ProjectMonthPlanGatherEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ProjectMonthPlanGatherEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ProjectMonthPlanGatherEntryInfo item)
    {
        return removeObject(item);
    }
    public ProjectMonthPlanGatherEntryInfo get(int index)
    {
        return(ProjectMonthPlanGatherEntryInfo)getObject(index);
    }
    public ProjectMonthPlanGatherEntryInfo get(Object key)
    {
        return(ProjectMonthPlanGatherEntryInfo)getObject(key);
    }
    public void set(int index, ProjectMonthPlanGatherEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ProjectMonthPlanGatherEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ProjectMonthPlanGatherEntryInfo item)
    {
        return super.indexOf(item);
    }
}