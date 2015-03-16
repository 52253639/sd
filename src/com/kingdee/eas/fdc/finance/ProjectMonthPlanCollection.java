package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ProjectMonthPlanCollection extends AbstractObjectCollection 
{
    public ProjectMonthPlanCollection()
    {
        super(ProjectMonthPlanInfo.class);
    }
    public boolean add(ProjectMonthPlanInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ProjectMonthPlanCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ProjectMonthPlanInfo item)
    {
        return removeObject(item);
    }
    public ProjectMonthPlanInfo get(int index)
    {
        return(ProjectMonthPlanInfo)getObject(index);
    }
    public ProjectMonthPlanInfo get(Object key)
    {
        return(ProjectMonthPlanInfo)getObject(key);
    }
    public void set(int index, ProjectMonthPlanInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ProjectMonthPlanInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ProjectMonthPlanInfo item)
    {
        return super.indexOf(item);
    }
}