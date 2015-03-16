package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ProjectYearPlanEntryCollection extends AbstractObjectCollection 
{
    public ProjectYearPlanEntryCollection()
    {
        super(ProjectYearPlanEntryInfo.class);
    }
    public boolean add(ProjectYearPlanEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ProjectYearPlanEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ProjectYearPlanEntryInfo item)
    {
        return removeObject(item);
    }
    public ProjectYearPlanEntryInfo get(int index)
    {
        return(ProjectYearPlanEntryInfo)getObject(index);
    }
    public ProjectYearPlanEntryInfo get(Object key)
    {
        return(ProjectYearPlanEntryInfo)getObject(key);
    }
    public void set(int index, ProjectYearPlanEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ProjectYearPlanEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ProjectYearPlanEntryInfo item)
    {
        return super.indexOf(item);
    }
}