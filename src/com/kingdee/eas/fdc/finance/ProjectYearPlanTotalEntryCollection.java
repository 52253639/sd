package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ProjectYearPlanTotalEntryCollection extends AbstractObjectCollection 
{
    public ProjectYearPlanTotalEntryCollection()
    {
        super(ProjectYearPlanTotalEntryInfo.class);
    }
    public boolean add(ProjectYearPlanTotalEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ProjectYearPlanTotalEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ProjectYearPlanTotalEntryInfo item)
    {
        return removeObject(item);
    }
    public ProjectYearPlanTotalEntryInfo get(int index)
    {
        return(ProjectYearPlanTotalEntryInfo)getObject(index);
    }
    public ProjectYearPlanTotalEntryInfo get(Object key)
    {
        return(ProjectYearPlanTotalEntryInfo)getObject(key);
    }
    public void set(int index, ProjectYearPlanTotalEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ProjectYearPlanTotalEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ProjectYearPlanTotalEntryInfo item)
    {
        return super.indexOf(item);
    }
}