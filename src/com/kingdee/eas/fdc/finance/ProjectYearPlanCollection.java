package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ProjectYearPlanCollection extends AbstractObjectCollection 
{
    public ProjectYearPlanCollection()
    {
        super(ProjectYearPlanInfo.class);
    }
    public boolean add(ProjectYearPlanInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ProjectYearPlanCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ProjectYearPlanInfo item)
    {
        return removeObject(item);
    }
    public ProjectYearPlanInfo get(int index)
    {
        return(ProjectYearPlanInfo)getObject(index);
    }
    public ProjectYearPlanInfo get(Object key)
    {
        return(ProjectYearPlanInfo)getObject(key);
    }
    public void set(int index, ProjectYearPlanInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ProjectYearPlanInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ProjectYearPlanInfo item)
    {
        return super.indexOf(item);
    }
}