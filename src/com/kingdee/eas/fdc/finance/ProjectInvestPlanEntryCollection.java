package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ProjectInvestPlanEntryCollection extends AbstractObjectCollection 
{
    public ProjectInvestPlanEntryCollection()
    {
        super(ProjectInvestPlanEntryInfo.class);
    }
    public boolean add(ProjectInvestPlanEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ProjectInvestPlanEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ProjectInvestPlanEntryInfo item)
    {
        return removeObject(item);
    }
    public ProjectInvestPlanEntryInfo get(int index)
    {
        return(ProjectInvestPlanEntryInfo)getObject(index);
    }
    public ProjectInvestPlanEntryInfo get(Object key)
    {
        return(ProjectInvestPlanEntryInfo)getObject(key);
    }
    public void set(int index, ProjectInvestPlanEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ProjectInvestPlanEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ProjectInvestPlanEntryInfo item)
    {
        return super.indexOf(item);
    }
}