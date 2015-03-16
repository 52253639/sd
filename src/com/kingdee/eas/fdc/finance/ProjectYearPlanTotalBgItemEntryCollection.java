package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ProjectYearPlanTotalBgItemEntryCollection extends AbstractObjectCollection 
{
    public ProjectYearPlanTotalBgItemEntryCollection()
    {
        super(ProjectYearPlanTotalBgItemEntryInfo.class);
    }
    public boolean add(ProjectYearPlanTotalBgItemEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ProjectYearPlanTotalBgItemEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ProjectYearPlanTotalBgItemEntryInfo item)
    {
        return removeObject(item);
    }
    public ProjectYearPlanTotalBgItemEntryInfo get(int index)
    {
        return(ProjectYearPlanTotalBgItemEntryInfo)getObject(index);
    }
    public ProjectYearPlanTotalBgItemEntryInfo get(Object key)
    {
        return(ProjectYearPlanTotalBgItemEntryInfo)getObject(key);
    }
    public void set(int index, ProjectYearPlanTotalBgItemEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ProjectYearPlanTotalBgItemEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ProjectYearPlanTotalBgItemEntryInfo item)
    {
        return super.indexOf(item);
    }
}