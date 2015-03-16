package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ProjectMonthPlanProDateEntryCollection extends AbstractObjectCollection 
{
    public ProjectMonthPlanProDateEntryCollection()
    {
        super(ProjectMonthPlanProDateEntryInfo.class);
    }
    public boolean add(ProjectMonthPlanProDateEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ProjectMonthPlanProDateEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ProjectMonthPlanProDateEntryInfo item)
    {
        return removeObject(item);
    }
    public ProjectMonthPlanProDateEntryInfo get(int index)
    {
        return(ProjectMonthPlanProDateEntryInfo)getObject(index);
    }
    public ProjectMonthPlanProDateEntryInfo get(Object key)
    {
        return(ProjectMonthPlanProDateEntryInfo)getObject(key);
    }
    public void set(int index, ProjectMonthPlanProDateEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ProjectMonthPlanProDateEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ProjectMonthPlanProDateEntryInfo item)
    {
        return super.indexOf(item);
    }
}