package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ProjectYearPlanDateEntryCollection extends AbstractObjectCollection 
{
    public ProjectYearPlanDateEntryCollection()
    {
        super(ProjectYearPlanDateEntryInfo.class);
    }
    public boolean add(ProjectYearPlanDateEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ProjectYearPlanDateEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ProjectYearPlanDateEntryInfo item)
    {
        return removeObject(item);
    }
    public ProjectYearPlanDateEntryInfo get(int index)
    {
        return(ProjectYearPlanDateEntryInfo)getObject(index);
    }
    public ProjectYearPlanDateEntryInfo get(Object key)
    {
        return(ProjectYearPlanDateEntryInfo)getObject(key);
    }
    public void set(int index, ProjectYearPlanDateEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ProjectYearPlanDateEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ProjectYearPlanDateEntryInfo item)
    {
        return super.indexOf(item);
    }
}