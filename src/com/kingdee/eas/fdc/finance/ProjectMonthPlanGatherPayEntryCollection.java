package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ProjectMonthPlanGatherPayEntryCollection extends AbstractObjectCollection 
{
    public ProjectMonthPlanGatherPayEntryCollection()
    {
        super(ProjectMonthPlanGatherPayEntryInfo.class);
    }
    public boolean add(ProjectMonthPlanGatherPayEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ProjectMonthPlanGatherPayEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ProjectMonthPlanGatherPayEntryInfo item)
    {
        return removeObject(item);
    }
    public ProjectMonthPlanGatherPayEntryInfo get(int index)
    {
        return(ProjectMonthPlanGatherPayEntryInfo)getObject(index);
    }
    public ProjectMonthPlanGatherPayEntryInfo get(Object key)
    {
        return(ProjectMonthPlanGatherPayEntryInfo)getObject(key);
    }
    public void set(int index, ProjectMonthPlanGatherPayEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ProjectMonthPlanGatherPayEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ProjectMonthPlanGatherPayEntryInfo item)
    {
        return super.indexOf(item);
    }
}