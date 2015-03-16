package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ProjectMonthPlanGatherDateEntryCollection extends AbstractObjectCollection 
{
    public ProjectMonthPlanGatherDateEntryCollection()
    {
        super(ProjectMonthPlanGatherDateEntryInfo.class);
    }
    public boolean add(ProjectMonthPlanGatherDateEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ProjectMonthPlanGatherDateEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ProjectMonthPlanGatherDateEntryInfo item)
    {
        return removeObject(item);
    }
    public ProjectMonthPlanGatherDateEntryInfo get(int index)
    {
        return(ProjectMonthPlanGatherDateEntryInfo)getObject(index);
    }
    public ProjectMonthPlanGatherDateEntryInfo get(Object key)
    {
        return(ProjectMonthPlanGatherDateEntryInfo)getObject(key);
    }
    public void set(int index, ProjectMonthPlanGatherDateEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ProjectMonthPlanGatherDateEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ProjectMonthPlanGatherDateEntryInfo item)
    {
        return super.indexOf(item);
    }
}