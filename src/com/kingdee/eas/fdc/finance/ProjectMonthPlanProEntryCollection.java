package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ProjectMonthPlanProEntryCollection extends AbstractObjectCollection 
{
    public ProjectMonthPlanProEntryCollection()
    {
        super(ProjectMonthPlanProEntryInfo.class);
    }
    public boolean add(ProjectMonthPlanProEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ProjectMonthPlanProEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ProjectMonthPlanProEntryInfo item)
    {
        return removeObject(item);
    }
    public ProjectMonthPlanProEntryInfo get(int index)
    {
        return(ProjectMonthPlanProEntryInfo)getObject(index);
    }
    public ProjectMonthPlanProEntryInfo get(Object key)
    {
        return(ProjectMonthPlanProEntryInfo)getObject(key);
    }
    public void set(int index, ProjectMonthPlanProEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ProjectMonthPlanProEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ProjectMonthPlanProEntryInfo item)
    {
        return super.indexOf(item);
    }
}